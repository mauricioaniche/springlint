var roles = ['controller', 'repository', 'service', 'entity', 'component', 'other'];
var thresholds = {
	controller: {
		 cbo: [26,29,34],
		lcom: [33,95,435],
		 nom: [16,22,37],
		 rfc: [62,78,110],
		 wmc: [57,83,130]
	},
	repository: {
		 cbo: [15,21,31],
		lcom: [36,106,351],
		 nom: [19,26,41],
		 rfc: [50,76,123],
		 wmc: [60,104,212]
	},
	service: {
		 cbo: [27,34,47],
		lcom: [133,271,622],
		 nom: [23,32,45],
		 rfc: [88,123,190],
		 wmc: [97,146,229]
	},
	entity: {
		 cbo: [16,20,25],
		lcom: [440,727,1844],
		 nom: [33,42,64],
		 rfc: [8,12,25],
		 wmc: [49,61,88]
	},
	component: {
		 cbo: [20,25,36],
		lcom: [50,123,433],
		 nom: [15,22,35],
		 rfc: [56,81,132],
		 wmc: [52,79,125]
	},
	other: {
		 cbo: [16,22,32],
		lcom: [147,368,1307],
		 nom: [23,34,59],
		 rfc: [48,71,119],
		 wmc: [65,101,187]
	}
};