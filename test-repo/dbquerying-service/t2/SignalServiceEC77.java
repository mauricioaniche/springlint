package jp.co.acroquest.endosnipe.web.dashboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.acroquest.endosnipe.common.entity.ItemType;
import jp.co.acroquest.endosnipe.common.logger.ENdoSnipeLogger;
import jp.co.acroquest.endosnipe.communicator.CommunicationClient;
import jp.co.acroquest.endosnipe.communicator.entity.Body;
import jp.co.acroquest.endosnipe.communicator.entity.Header;
import jp.co.acroquest.endosnipe.communicator.entity.Telegram;
import jp.co.acroquest.endosnipe.communicator.entity.TelegramConstants;
import jp.co.acroquest.endosnipe.data.dao.JavelinMeasurementItemDao;
import jp.co.acroquest.endosnipe.web.dashboard.constants.LogMessageCodes;
import jp.co.acroquest.endosnipe.web.dashboard.constants.SignalConstants;
import jp.co.acroquest.endosnipe.web.dashboard.constants.TreeMenuConstants;
import jp.co.acroquest.endosnipe.web.dashboard.dao.SignalInfoDao;
import jp.co.acroquest.endosnipe.web.dashboard.dto.SignalDefinitionDto;
import jp.co.acroquest.endosnipe.web.dashboard.dto.SignalTreeMenuDto;
import jp.co.acroquest.endosnipe.web.dashboard.dto.TreeMenuDto;
import jp.co.acroquest.endosnipe.web.dashboard.entity.SignalInfo;
import jp.co.acroquest.endosnipe.web.dashboard.manager.ConnectionClient;
import jp.co.acroquest.endosnipe.web.dashboard.manager.DatabaseManager;
import jp.co.acroquest.endosnipe.web.dashboard.manager.EventManager;
import jp.co.acroquest.endosnipe.web.dashboard.manager.ResourceSender;
import jp.co.acroquest.endosnipe.web.dashboard.util.SignalUtil;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wgp.manager.WgpDataManager;

/**
 * シグナル定義のサービスクラス。
 * 
 * @author miyasaka
 * 
 */
@Service
public class SignalService2
{

    /** ロガー。 */
    private static final ENdoSnipeLogger LOGGER = ENdoSnipeLogger.getLogger(MapService.class);

    /**
     * デフォルトのシグナル状態(監視停止中)
     */
    private static final int DEFAULT_SIGNAL_STATE = -1;

    /**
     * シグナル情報Dao
     */
    @Autowired
    protected SignalInfoDao signalInfoDao;

    /**
     * コンストラクタ
     */
    public SignalService()
    {

    }

    /**
     * すべてのシグナルデータを返す。
     * 
     * @return シグナルデータ一覧
     */
    public List<SignalDefinitionDto> getAllSignal()
    {
        List<SignalInfo> signalList = null;
        try
        {
            signalList = signalInfoDao.selectAll();
        }
        catch (PersistenceException pEx)
        {
            Throwable cause = pEx.getCause();
            if (cause instanceof SQLException)
            {
                SQLException sqlEx = (SQLException)cause;
                LOGGER.log(LogMessageCodes.SQL_EXCEPTION, sqlEx, sqlEx.getMessage());
            }
            LOGGER.log(LogMessageCodes.SQL_EXCEPTION, pEx, pEx.getMessage());

            return new ArrayList<SignalDefinitionDto>();
        }

        List<SignalDefinitionDto> definitionDtoList = new ArrayList<SignalDefinitionDto>();

        for (SignalInfo signalInfo : signalList)
        {
            SignalDefinitionDto signalDto = this.convertSignalDto(signalInfo);
            // 初期状態にはデフォルト値を設定とする。
            signalDto.setSignalValue(DEFAULT_SIGNAL_STATE);
            definitionDtoList.add(signalDto);
        }

        sendGetAllStateRequest(signalList);
        return definitionDtoList;
    }

    /**
     * シグナルの全状態を取得するリクエストを生成する。
     * 
     * @param signalList
     *            シグナル定義情報一覧
     */
    private void sendGetAllStateRequest(final List<SignalInfo> signalList)
    {
        ConnectionClient connectionClient = ConnectionClient.getInstance();
        List<CommunicationClient> clientList = connectionClient.getClientList();
        for (CommunicationClient communicationClient : clientList)
        {
            Telegram telegram = createAllStateTelegram(signalList);
            communicationClient.sendTelegram(telegram);
        }

    }

    /**
     * 閾値判定定義情報を更新するリクエストを生成する。
     * 
     * @param signalDefinitionDto 閾値判定定義情報
     * @param type 操作種別(add, update, deleteのいずれか)s
     */
    private void sendSignalDefinitionRequest(final SignalDefinitionDto signalDefinitionDto,
            final String type)
    {
        ConnectionClient connectionClient = ConnectionClient.getInstance();
        List<CommunicationClient> clientList = connectionClient.getClientList();
        for (CommunicationClient communicationClient : clientList)
        {
            Telegram telegram = null;
            if (SignalConstants.OPERATION_TYPE_ADD.equals(type))
            {
                telegram = createAddSignalTelegram(signalDefinitionDto);
            }
            else if (SignalConstants.OPERATION_TYPE_UPDATE.equals(type))
            {
                telegram = createUpdateSignalTelegram(signalDefinitionDto);
            }
            else if (SignalConstants.OPERATION_TYPE_DELETE.equals(type))
            {
                telegram = createDeleteSignalTelegram(signalDefinitionDto);
            }
            if (telegram != null)
            {
                communicationClient.sendTelegram(telegram);
            }
        }

    }

    /**
     * 全閾値情報を取得する電文を作成する。
     * 
     * @param signalList
     *            シグナル定義情報のリスト
     * @return 全閾値情報を取得する電文
     */
    private Telegram createAllStateTelegram(final List<SignalInfo> signalList)
    {
        Telegram telegram = new Telegram();

        Header requestHeader = new Header();
        requestHeader.setByteTelegramKind(TelegramConstants.BYTE_TELEGRAM_KIND_SIGNAL_STATE);
        requestHeader.setByteRequestKind(TelegramConstants.BYTE_REQUEST_KIND_REQUEST);

        int dtoCount = signalList.size();

        // 閾値判定定義情報のID
        Body signalIdBody = new Body();
        signalIdBody.setStrObjName(TelegramConstants.OBJECTNAME_RESOURCEALARM);
        signalIdBody.setStrItemName(TelegramConstants.ITEMNAME_SIGNAL_ID);
        signalIdBody.setByteItemMode(ItemType.ITEMTYPE_LONG);
        signalIdBody.setIntLoopCount(dtoCount);
        Long[] signalIds = new Long[dtoCount];

        // 閾値判定定義情報名
        Body signalNameBody = new Body();
        signalNameBody.setStrObjName(TelegramConstants.OBJECTNAME_RESOURCEALARM);
        signalNameBody.setStrItemName(TelegramConstants.ITEMNAME_ALARM_ID);
        signalNameBody.setByteItemMode(ItemType.ITEMTYPE_STRING);
        signalNameBody.setIntLoopCount(dtoCount);
        String[] signalNames = new String[dtoCount];

        for (int cnt = 0; cnt < dtoCount; cnt++)
        {
            SignalInfo signalInfo = signalList.get(cnt);
            signalNames[cnt] = signalInfo.signalName;
            signalIds[cnt] = Long.valueOf(signalInfo.signalId);
        }
        signalIdBody.setObjItemValueArr(signalIds);
        signalNameBody.setObjItemValueArr(signalNames);

        Body[] requestBodys = { signalIdBody, signalNameBody };

        telegram.setObjHeader(requestHeader);
        telegram.setObjBody(requestBodys);

        return telegram;
    }

    /**
     * 閾値判定定義情報の追加を通知する電文を作成する。
     * 
     * @param signalDefinitionDto シグナル定義情報のリスト
     * @return 全閾値情報を取得する電文
     */
    private Telegram createAddSignalTelegram(final SignalDefinitionDto signalDefinitionDto)
    {
        return SignalUtil.createAddSignalDefinition(signalDefinitionDto,
                                                    TelegramConstants.ITEMNAME_SIGNAL_ADD);
    }

    /**
     * 閾値判定定義情報の更新を通知する電文を作成する。
     * 
     * @param signalDefinitionDto シグナル定義情報のリスト
     * @return 全閾値情報を取得する電文
     */
    private Telegram createUpdateSignalTelegram(final SignalDefinitionDto signalDefinitionDto)
    {
        return SignalUtil.createAddSignalDefinition(signalDefinitionDto,
                                                    TelegramConstants.ITEMNAME_SIGNAL_UPDATE);
    }

    /**
     * 閾値判定定義情報の削除を通知する電文を作成する。
     * 
     * @param signalDefinitionDto シグナル定義情報のリスト
     * @return 全閾値情報を取得する電文
     */
    private Telegram createDeleteSignalTelegram(final SignalDefinitionDto signalDefinitionDto)
    {
        return SignalUtil.createAddSignalDefinition(signalDefinitionDto,
                                                    TelegramConstants.ITEMNAME_SIGNAL_DELETE);
    }

    /**
     * シグナル定義をDBに登録する。
     * 
     * @param signalInfo
     *            シグナル定義
     * @return シグナル定義のDTOオブジェクト
     */
    @Transactional
    public SignalDefinitionDto insertSignalInfo(final SignalInfo signalInfo)
    {
        int signalId = 0;
        try
        {
            signalInfoDao.insert(signalInfo);
            signalId = signalInfoDao.selectSequenceNum(signalInfo);
        }
        catch (DuplicateKeyException dkEx)
        {
            LOGGER.log(LogMessageCodes.SQL_EXCEPTION, dkEx, dkEx.getMessage());
            return new SignalDefinitionDto();
        }

        SignalDefinitionDto signalDefinitionDto = this.convertSignalDto(signalInfo);
        signalDefinitionDto.setSignalId(signalId);

        // 初期状態にはデフォルト値を設定とする。
        signalDefinitionDto.setSignalValue(DEFAULT_SIGNAL_STATE);

        // DataCollectorにシグナル定義の追加を通知する。
        sendSignalDefinitionRequest(signalDefinitionDto, SignalConstants.OPERATION_TYPE_ADD);

        // 各クライアントにシグナル定義の追加を送信する。
        sendSignalDefinition(signalDefinitionDto, "add");

        return signalDefinitionDto;
    }

    /**
     * シグナル定義をDBから取得する。
     * @param signalName シグナル名
     * @return シグナル定義のDTOオブジェクト
     */
    public SignalDefinitionDto getSignalInfo(final String signalName)
    {
        SignalInfo signalInfo = signalInfoDao.selectByName(signalName);
        SignalDefinitionDto defitionDto = this.convertSignalDto(signalInfo);
        return defitionDto;
    }

    /**
     * シグナル定義を更新する。
     * 
     * @param signalInfo
     *            シグナル定義
     * @return {@link SignalDefinitionDto}オブジェクト
     */
    public SignalDefinitionDto updateSignalInfo(final SignalInfo signalInfo)
    {
        try
        {
            SignalInfo beforeSignalInfo = signalInfoDao.selectById(signalInfo.signalId);
            if (beforeSignalInfo == null)
            {
                return new SignalDefinitionDto();
            }

            signalInfoDao.update(signalInfo);

            String beforeItemName = beforeSignalInfo.signalName;
            String afterItemName = signalInfo.signalName;

            this.updateMeasurementItemName(beforeItemName, afterItemName);
        }
        catch (PersistenceException pEx)
        {
            Throwable cause = pEx.getCause();
            if (cause instanceof SQLException)
            {
                SQLException sqlEx = (SQLException)cause;
                LOGGER.log(LogMessageCodes.SQL_EXCEPTION, sqlEx, sqlEx.getMessage());
            }
            else
            {
                LOGGER.log(LogMessageCodes.SQL_EXCEPTION, pEx, pEx.getMessage());
            }

            return new SignalDefinitionDto();
        }
        catch (SQLException sqlEx)
        {
            LOGGER.log(LogMessageCodes.SQL_EXCEPTION, sqlEx, sqlEx.getMessage());
            return new SignalDefinitionDto();
        }

        SignalDefinitionDto signalDefinitionDto = this.convertSignalDto(signalInfo);
        signalDefinitionDto.setSignalValue(-1);

        sendSignalDefinitionRequest(signalDefinitionDto, SignalConstants.OPERATION_TYPE_UPDATE);
        // 各クライアントにシグナル定義の変更を送信する。
        sendSignalDefinition(signalDefinitionDto, "update");

        return signalDefinitionDto;
    }

    /**
     * シグナル名に該当するシグナル定義をDBから削除する。
     *
     * @param signalName
     *            シグナル名
     */
    public void deleteSignalInfo(final String signalName)
    {
        SignalDefinitionDto signalDefinitionDto = null;
        try
        {
            // 削除前に定義情報を取得しておく。
            signalDefinitionDto = getSignalInfo(signalName);

            signalInfoDao.delete(signalName);
            this.deleteMeasurementItem(signalName);
        }
        catch (PersistenceException pEx)
        {
            Throwable cause = pEx.getCause();
            if (cause instanceof SQLException)
            {
                SQLException sqlEx = (SQLException)cause;
                LOGGER.log(LogMessageCodes.SQL_EXCEPTION, sqlEx, sqlEx.getMessage());
            }
            else
            {
                LOGGER.log(LogMessageCodes.SQL_EXCEPTION, pEx, pEx.getMessage());
            }
        }
        catch (SQLException sqlEx)
        {
            LOGGER.log(LogMessageCodes.SQL_EXCEPTION, sqlEx, sqlEx.getMessage());
        }

        // 各クライアントにシグナル定義の削除を送信する。
        sendSignalDefinition(signalDefinitionDto, "delete");
    }

    /**
     * SignalDefinitionDtoオブジェクトをSignalInfoオブジェクトに変換する。
     * 
     * @param definitionDto
     *            SignalDefinitionDtoオブジェクト
     * 
     * @return SignalInfoオブジェクト
     */
    public SignalInfo convertSignalInfo(final SignalDefinitionDto definitionDto)
    {
        SignalInfo signalInfo = new SignalInfo();

        signalInfo.signalId = definitionDto.getSignalId();
        signalInfo.signalName = definitionDto.getSignalName();
        signalInfo.matchingPattern = definitionDto.getMatchingPattern();
        signalInfo.level = definitionDto.getLevel();
        signalInfo.patternValue = definitionDto.getPatternValue();
        signalInfo.escalationPeriod = definitionDto.getEscalationPeriod();

        return signalInfo;
    }

    /**
     * SignalInfoオブジェクトをSignalDefinitionDtoオブジェクトに変換する。
     * 
     * @param signalInfo
     *            SignalInfoオブジェクト
     * @return SignalDefinitionDtoオブジェクト
     */
    private SignalDefinitionDto convertSignalDto(final SignalInfo signalInfo)
    {

        SignalDefinitionDto definitionDto = new SignalDefinitionDto();

        definitionDto.setSignalId(signalInfo.signalId);
        definitionDto.setSignalName(signalInfo.signalName);
        definitionDto.setMatchingPattern(signalInfo.matchingPattern);
        definitionDto.setLevel(signalInfo.level);
        definitionDto.setPatternValue(signalInfo.patternValue);
        definitionDto.setEscalationPeriod(signalInfo.escalationPeriod);

        return definitionDto;
    }

    /**
     * javelin_measurement_itemテーブルのMEASUREMENT_ITEM_NAMEを更新する。
     * 
     * @param beforeItemName
     *            更新前のMEASUREMENT_ITEM_NAME
     * @param afterItemName
     *            更新前のMEASUREMENT_ITEM_NAME
     * @throws SQLException
     *             SQL 実行時に例外が発生した場合
     */
    private void updateMeasurementItemName(final String beforeItemName, final String afterItemName)
        throws SQLException
    {
        DatabaseManager dbMmanager = DatabaseManager.getInstance();
        String dbName = dbMmanager.getDataBaseName(1);

        JavelinMeasurementItemDao.updateMeasurementItemName(dbName, beforeItemName, afterItemName);
    }

    /**
     * javelin_measurement_itemテーブルの指定したMEASUREMENT_ITEM_NAMEのレコードを削除する。
     * 
     * @param itemName
     *            削除するレコードの MEASUREMENT_ITEM_NAME
     * @throws SQLException
     *             SQL 実行時に例外が発生した場合
     */
    private void deleteMeasurementItem(final String itemName)
        throws SQLException
    {
        DatabaseManager dbMmanager = DatabaseManager.getInstance();
        String dbName = dbMmanager.getDataBaseName(1);

        JavelinMeasurementItemDao.deleteByMeasurementItemId(dbName, itemName);
    }

    /**
     * 同一のシグナル名を持つ閾値判定定義情報がDBに存在するかどうか。
     * @param signalName シグナル名
     * @return 同一シグナル名を保持する閾値判定定義情報が存在する場合にtrueを返し、存在しない場合にfalseを返す。
     */
    public boolean hasSameSignalName(final String signalName)
    {
        SignalInfo signalInfo = this.signalInfoDao.selectByName(signalName);
        if (signalInfo == null)
        {
            // 同一シグナル名を持つ閾値判定定義情報が存在しない場合
            return false;
        }
        return true;
    }

    /**
     * 同一のシグナル名を持つ閾値判定定義情報がDBに存在するかどうか。<br />
     * ただし、同一のシグナルIDを保持する場合は、更新対象がDBに定義された閾値判定定義情報と同一とみなし、falseを返す。
     * @param signalId シグナルID
     * @param signalName シグナル名
     * @return 同一シグナル名を保持する閾値判定定義情報が存在する場合にtrueを返し、存在しない場合にfalseを返す。
     */
    public boolean hasSameSignalName(final long signalId, final String signalName)
    {
        SignalInfo signalInfo = this.signalInfoDao.selectByName(signalName);
        if (signalInfo == null)
        {
            // 同一シグナル名を持つ閾値判定定義情報が存在しない場合
            return false;
        }
        else if (signalInfo.signalId == signalId)
        {
            // シグナル名が一致する閾値判定定義情報が更新対象自身の場合
            return false;
        }
        return true;
    }

    /**
     * シグナル定義DTOをツリーメニュー用のDTOに変換する。
     * @param signalDto シグナル定義情報
     * @return ツリーメニュー用のDTO
     */
    public SignalTreeMenuDto convertSignalTreeMenu(final SignalDefinitionDto signalDto)
    {

        SignalTreeMenuDto treeMenu = new SignalTreeMenuDto();

        String signalName = signalDto.getSignalName();

        // シグナル名から親階層のツリーID名を取得する。
        // ※一番右のスラッシュ区切りまでを親階層とする。
        int terminateParentTreeIndex = signalName.lastIndexOf("/");
        String parentTreeId = signalName.substring(0, terminateParentTreeIndex);

        // シグナル表示名を取得する。
        // ※一番右のスラッシュ区切り以降を表示名とする。
        String signalDisplayName = signalName.substring(terminateParentTreeIndex + 1);

        treeMenu.setId(signalName);
        treeMenu.setTreeId(signalName);
        treeMenu.setParentTreeId(parentTreeId);
        treeMenu.setData(signalDisplayName);
        treeMenu.setSignalValue(signalDto.getSignalValue());
        treeMenu.setType(TreeMenuConstants.TREE_MENU_TYPE_SIGNAL);
        treeMenu.setIcon("signal_" + signalDto.getSignalValue());

        return treeMenu;
    }

    /**
     * シグナル定義情報の追加・更新・削除を各クライアントに送信する。
     * @param signalDefinitionDto シグナル定義情報
     * @param type 送信タイプ(add:追加 update:変更 delete:削除)
     */
    private void sendSignalDefinition(final SignalDefinitionDto signalDefinitionDto,
            final String type)
    {
        // 各クライアントにシグナル定義の追加を通知する。
        EventManager eventManager = EventManager.getInstance();
        WgpDataManager dataManager = eventManager.getWgpDataManager();
        ResourceSender resourceSender = eventManager.getResourceSender();
        if (dataManager != null && resourceSender != null)
        {
            List<TreeMenuDto> treeMenuDtoList = new ArrayList<TreeMenuDto>();
            treeMenuDtoList.add(this.convertSignalTreeMenu(signalDefinitionDto));
            resourceSender.send(treeMenuDtoList, type);
        }
    }
}