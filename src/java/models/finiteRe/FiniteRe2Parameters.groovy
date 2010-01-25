package models.finiteRe

import org.pillarone.riskanalytics.core.parameterization.ComboBoxTableMultiDimensionalParameter
import org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.ReinsuranceContractStrategyFactory
import org.pillarone.riskanalytics.domain.pc.reinsurance.contracts.ReinsuranceContractType
import org.pillarone.riskanalytics.domain.pc.constants.PremiumBase
import org.pillarone.riskanalytics.domain.utils.RandomDistributionFactory
import org.pillarone.riskanalytics.domain.utils.DistributionType
import org.pillarone.riskanalytics.domain.utils.DistributionModifierFactory
import org.pillarone.riskanalytics.domain.utils.DistributionModifier
import org.pillarone.riskanalytics.domain.pc.claims.RiskAllocatorType
import org.pillarone.riskanalytics.domain.pc.claims.RiskAllocatorStrategyFactory
import org.pillarone.riskanalytics.domain.pc.constants.Exposure
import org.pillarone.riskanalytics.domain.pc.constants.FrequencyBase
import org.pillarone.riskanalytics.domain.pc.generators.claims.ClaimsGeneratorStrategyFactory
import org.pillarone.riskanalytics.domain.pc.generators.claims.ClaimsGeneratorType
import org.pillarone.riskanalytics.domain.pc.constants.FrequencySeverityClaimType
import org.pillarone.riskanalytics.domain.pc.underwriting.IUnderwritingInfoMarker
import org.pillarone.riskanalytics.core.parameterization.ComboBoxTableMultiDimensionalParameter
import org.pillarone.riskanalytics.core.parameterization.ConstrainedMultiDimensionalParameter
import org.pillarone.riskanalytics.domain.pc.generators.copulas.CopulaStrategyFactory
import org.pillarone.riskanalytics.domain.pc.generators.copulas.PerilCopulaType
import org.pillarone.riskanalytics.core.parameterization.ComboBoxMatrixMultiDimensionalParameter
import org.pillarone.riskanalytics.core.parameterization.ConstraintsFactory
import org.pillarone.riskanalytics.core.parameterization.ConstrainedString
import org.pillarone.riskanalytics.core.parameterization.TableMultiDimensionalParameter

model=models.finiteRe.FiniteReModel
periodCount=3
displayName='ART Example'
periodLabels=["2009","2010","2011"]
components {
	lineOfBusinessReinsurance {
		subPropertyStopLoss {
			parmInuringPriority[2]=0
			parmInuringPriority[1]=0
			parmInuringPriority[0]=0
			parmCoveredPerils[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
["property attritional","property cat"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
["property attritional","property cat"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
["property attritional","property cat"]
"""
,["perils"], PerilMarker)
			parmContractStrategy[1]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[2]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[0]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmCoveredLines[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
		}
		subMarineStopLoss {
			parmContractStrategy[2]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[1]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[0]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmCoveredLines[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredPerils[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
["marine"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
["marine"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
["marine"]
"""
,["perils"], PerilMarker)
			parmInuringPriority[2]=0
			parmInuringPriority[0]=0
			parmInuringPriority[1]=0
		}
		subMotorStopLoss {
			parmInuringPriority[2]=0
			parmInuringPriority[1]=0
			parmInuringPriority[0]=0
			parmCoveredLines[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredPerils[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
["motor"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
["motor"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
["motor"]
"""
,["perils"], PerilMarker)
			parmContractStrategy[0]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[1]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[2]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
		}
		subHullStopLoss {
			parmCoveredLines[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmCoveredLines[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
			parmContractStrategy[0]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[1]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmContractStrategy[2]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":1.0E7,"limit":2.0E7,"coveredByReinsurer":1.0,])
			parmCoveredPerils[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
["hull"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
["hull"]
"""
,["perils"], PerilMarker)
			parmCoveredPerils[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
["hull"]
"""
,["perils"], PerilMarker)
			parmInuringPriority[2]=0
			parmInuringPriority[1]=0
			parmInuringPriority[0]=0
		}
	}
	claimsGenerators {
		subPropertyCat {
			parmAssociateExposureInfo[2]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[0]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[1]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmUnderwritingInformation[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmClaimsModel[0]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.FREQUENCY_SEVERITY, ["frequencyBase":FrequencyBase.ABSOLUTE,"frequencyDistribution":RandomDistributionFactory.getDistribution(DistributionType.POISSON, [lambda:2.0]),"frequencyModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.DISCRETEEMPIRICAL, [discreteEmpiricalValues:new TableMultiDimensionalParameter(
"""\
[[12143709,6309104,5009605,7308624,5421975,8234951,5360257,10123741,5483790,5948370,5552701,9668615,6546873,11444383,5286637,6592048,9106413,24513248,7822610,5421025,8845477,5157566,8890045,10105833,6938685,6559507,15063835,5000194,5019263,5615625,6627740,6823769,6019917,5742901,5473295,9523641,5820067,6558200,9742106,7304628,6169074,5404927,6123942,6521070,32317247,5715472,5375620,5524085,5498473,5160719,5995636,5717195,11985224,9166553,5236385,10693444,5237640,8322154,7026311,5184958,12837529,11471088,10508849,6013323,5530835,51845417,9869862,5086828,6027539,9760672,6753592,5601214,9386158,5645324,5000797,5554840,37185375,7663869,5524348,7948322,7956412,7530222,5148483,5588862,11337440,8501623,5381239,5762104,6960925,10073860,5468222,5219089,6060909,13239638,5641543,5234598,5116473,5946367,5077298,7094629,7814760,6399826,8584767,6602957,8118750,5552881,8631576,6606201,8151894,6335561,6629158,5085569,6541944,5316592,5317108,6275440,7048743,5915027,5362410,8588664,18711982,8487580,5162992,10430637,5141775,6288608,14392336,5649701,5281461,8882058,14371777,5853914,5558136,5419657,18741254,7051538,10980642,5794476,5117543,6368405,5486367,6700482,6809396,5748030,5791493,5049182,6218981,5846710,7359082,10970465,6559091,5175675,14783944,9681566,5997945,5871231,15199480,6033768,7489194,27074960,5075427,5815676,5554599,13897860,6888495,5111648,8779700,7927591,7717788,17398884,11472372,8751212,5530023,5306035,5524973,5481944,6994590,7725750,6748060,5117355,5989059,19744060,8045290,6013981,7805893,5655630,5163917,6751774,5733373,6503114,12661058,7624454,8359463,5221232,6493136,6151361,7582107,5726818,6065605,24413057,5249954,5466559,5577334,8057398,8245827,9242655,6057678,10748289,8064766,8240900,9972199,7433358,6226834,5062959,7180977,6967568,7219336,5843821,6911621,5391204,7608044,5150978,11489820,5511555,5746586,11865715,11818899,10311355,5857973,12679192,9029121,7680716,12549553,9214916,6237739,5848667,6108661,5128378,5990068,5951412,11159428,12032947,18703980,5862378,8022245,7466807,5587326,11307748,5505839,8673867,5322495,5231116,8323516,6741791,6318321,9132090,5972515,10775978,18528785,23503012,5694632,6195976,8379984,12310413,8218603,8821427,11811254,6003359,6836450,6790518,7588345,12325336,7219477,20312621,8468451,6197182,7081770,13894665,7351439,8202912,7709742,5890350,5475953,6165758,5337770,10071256,8208937,6137840,5390492,11516498,7516192,48587279,5217771,5594436,5177813,8633325,5360070,11741314,6553097,6740944,5087661,7317441,5544169,5504165,12331677,12003379,5010569,10213178,12676814,6661675,7663628,8966324,7941245,6393350,7030447,7034454,10462868,5581431,8808247,9763795,9462356,9669084,10140428,6206637,7346714,8779661,8997086,17872663,21893634,6973782,6424934,10292726,5522344,8944982,5445126,7153975,7263376,6313464,11123955,6842061,6559553,6209238,5130156,5985252,5198664,6979583,7528201,8966271,7462490,5431837,8708906,6874565,5220264,5050535,5762619,7987921,14722860,19450804,8916802,5744649,7379128,5554196,8715140,5376206,8797298,11259454,6703397,5137636,6345638,10350952,5412427,5079266,8475518,6962955,5086684,26742979,7003468,7109128,9332652,12694286,5126933,5934823,5467061,6422109,5195578,5143037,5769679,12784608,22059795,8904289,5001483,8507717,5044674,5383179,5021184,5333645,7962200,8115090,6019094,8456533,10603254,5287297,5186319,6022328,6149469,5690777,8764250,5840141,9521018,13177011,6668245,6258122,5195869,5682221,11945327,5597687,7372269,6571371,5202237,12527848,5142342,7893901,20897544,8196644,5854402,5154904,7072314,8485984,5972898,7642137,6985717,5069887,13779980,6053617,6051260,10577027,6516340,6355692,7522806,5499321,6753111,7777007,6814053,8189743,7202969,10443710,11643973,5210224,5635696,5113686,5547840,8330604,5166309,5348745,9012809,6324726,5734509,7557576,11957598,5077252,8019883,7776840,11239742,5456925,8235534,5224294,5117956,7894828,5046000,5091447,5075876,6423354,8817595,34842947,5541280,9376970,9429245,11387168,5814404,5293554,9456195,6991347,6194481,5122335,5013174,5820409,5675294,6473600,13988129,6203097,5727297,10546633,5789808,13440027,11086764,7514155,8673333,5354335,7234338,5858327,6538712,5537781,12659151,6181322,6478598,8336414,5267580,6613787,9780715,7860912,5839675,8211261,14870082,12306214,8599640,5658685,6024500,6933706,6057696,6377488,9397748,5224385,21203252,8143611,11129782,9190362,6270279,15672437,5619036,5095607,5208006,5468825,6815849,5532690,10287611,5018800,8649057,20066386,5662026,7392812,8258436,5858049,6274449,8497371,5289226,5320063,6035746,5546451,5789930,5336000,5207448,13465586,5925303,6981691,6864469,6352401,8675333,5874208,12200291,5142921,7039946,5900696,5991836,5783631,7239533,5563794,5748089,5719628,5013656,6021167,5101279,11464134,8341445,7179923,8621672,12614821,8030320,7147879,5272539,6152355,7067032,7234120,18262381,5481918,7726736,5629127,6207683,20883328,5872455,5984778,6303198,5083729,6127679,7355179,7061191,11429151,7533946,6924545,7529421,5098534,46078433,5340124,6210610,5751172,7055421,6940899,5947996,21405354,8723281,12082661,6375910,7209220,6189406,5780827,6736972,5442314,5505839,6655351,5027976,6330024,49158630,9987800,5177374,7661102,5537802,5257435,5935421,7859207,6886212,5090730,10877706,6482867,10878373,5433424,5177990,6683762,6371861,5028062,5080948,5200539,5083750,5401870,7044233,6478690,23799584,5148901,11219489,7064137,11541675,5115976,5213264,11076484,7010320,6934183,5026265,7542920,10613278,46101465,6273688,5804776,6153131,5545347,5539821,6276739,7932170,7704697,9040031,5513460,8425471,9429392,6955144,6424605,5356565,5096044,6415783,7647004,5501486,6133310,6585113,17270886,11578113,11452235,48574235,9055384,5475197,10403985,5377189,12049725,5899008,5727492,7258884,5126629,12934261,6057781,9334012,8209922,8138423,6778042,6429444,7569610,8004080,5567899,8759122,15072363,9821844,8449231,9965983,5013978,5399473,5911180,8485998,23172549,8477856,6489819,13804896,6044632,7538184,5003012,5411390,5749825,6081111,6396682,9456514,5701639,5175570,6713826,24482952,6136620,5631436,9101979,7329076,5015177,27738479,6539943,5780052,7744476,5719840,6414404,7625161,5266825,6432086,8507591,18214218,5043431,10217450,15319846,27432969,15189456,5284609,11918973,5343039,5979133,5981549,5701449,5957903,6839181,5166246,6025969,7095889,5296792,5588933,5809402,5157340,9668742,5650368,6177407,6155790,5782425,5280940,12249745,7900760,5661124,5197783,5270699,5690879,5101592,5323698,6470175,15012123,6278626,8194014,7009805,22318347,5408364,8558226,5941737,33221255,5950559,23350593,10989560,7197237,5055974,5427757,5117707,9378443,5156042,9272095,5057023,5625428,5758801,6972961,6956722,11977223,7502190,5364891,5055032,8878231,6795188,7064871,6192159,7141314,6156695,6330962,7161783,6557561,5123341,22526710,10828986,6050027,6860387,14730400,6125216,15926065,5062306,8647677,9461876,5450050,5368490,7150130,6493304,7118651,5291958,5694976,5146543,5233876,8277017,5053801,6819223,5762346,11256312,6851415,55353236,6950471,6956954,5886664,5616747,6249140,5542907,5149209,6239048,5230173,8357067,5761462,6547344,5454784,7895130,10177397,5523607,5015703,5612484,7423062,5802780,10309817,10758089,6021635,5997249,5033514,6879566,5346597,5244907,5110837,5411190,5350801,5072796,25181730,7682708,6081128,5130726,5097739,9614214,6229216,5234495,5379218,7888885,6248549,5207845,6101145,5092420,5673946,6613865,6385626,8979469,5361742,6651311,5314707,5438021,6174465,5325482,6958415,6375075,15627466,7790436,5222526,7265412,6218669,6600878,7660347,5428253,5975465,6496742,7322979,5719828,6612235,5697529,7023455,5912295,10385165,7946025,9843818,5346535,5426855,6836748,5128348,6809814,5889820,5849357,5550608,5009404,7957799,8744549,5897951,6444590,9468889,7587356,5046709,5714762,17193503,11801180,5852004,13137141,6490726,5472475,5675265,5583020,5115677,6292289,12797334,6738602,6853932,5542403,5095932,5276273,5508711,11428454,29656573,5035981,6320110,6011912,6800431,7336251,6117235,5423317,7109935,5068188,12269053,7561197,5202632,6889959,5283937,5259498,5656983,8892082,8074105,6936435,5625490,6694280,14464850,5427190,8494794,11385036,7785355,5476945,5284402,6678595,5287177,10056597,6726630,5738724,6995640,5641921,7104428,5557164,5519772,6697823,10289985,7564261,5201820,5296349,6094298,5832507],
[0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010,0.0010]
]
"""
,["observations","probabilities"])]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"produceClaim":FrequencySeverityClaimType.AGGREGATED_EVENT,])
			parmClaimsModel[1]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.FREQUENCY_SEVERITY, ["frequencyBase":FrequencyBase.ABSOLUTE,"frequencyDistribution":RandomDistributionFactory.getDistribution(DistributionType.POISSON, [lambda:2.0]),"frequencyModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:6000000.0, stDev:6000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"produceClaim":FrequencySeverityClaimType.AGGREGATED_EVENT,])
			parmClaimsModel[2]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.FREQUENCY_SEVERITY, ["frequencyBase":FrequencyBase.ABSOLUTE,"frequencyDistribution":RandomDistributionFactory.getDistribution(DistributionType.POISSON, [lambda:2.0]),"frequencyModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:6000000.0, stDev:6000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),"produceClaim":FrequencySeverityClaimType.AGGREGATED_EVENT,])
		}
		subPropertyAttritional {
			parmClaimsModel[2]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:4000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[0]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:4000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[1]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:4000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmAssociateExposureInfo[2]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[0]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[1]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmUnderwritingInformation[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
		}
		subMotor {
			parmUnderwritingInformation[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmClaimsModel[2]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:7000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[1]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:7000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[0]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:4000000.0, stDev:7000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmAssociateExposureInfo[2]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[1]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[0]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
		}
		subHull {
			parmClaimsModel[1]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:3000000.0, stDev:6000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[2]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:3000000.0, stDev:6000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[0]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:3000000.0, stDev:6000000.0]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmAssociateExposureInfo[1]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[0]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[2]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmUnderwritingInformation[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
		}
		subMarine {
			parmClaimsModel[2]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:6000000.0, stDev:1.2E7]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[0]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:6000000.0, stDev:1.2E7]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmClaimsModel[1]=ClaimsGeneratorStrategyFactory.getStrategy(ClaimsGeneratorType.ATTRITIONAL, ["claimsSizeBase":Exposure.ABSOLUTE,"claimsSizeDistribution":RandomDistributionFactory.getDistribution(DistributionType.LOGNORMAL, [mean:6000000.0, stDev:1.2E7]),"claimsSizeModification":DistributionModifierFactory.getModifier(DistributionModifier.NONE, [:]),])
			parmAssociateExposureInfo[0]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[1]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmAssociateExposureInfo[2]=RiskAllocatorStrategyFactory.getAllocatorStrategy(RiskAllocatorType.NONE, [:])
			parmUnderwritingInformation[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
			parmUnderwritingInformation[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Underwriting Information"], IUnderwritingInfoMarker)
		}
	}
	wholeAccountStopLoss {
		parmContractStrategy[1]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":2.0E7,"limit":3.0E7,"coveredByReinsurer":1.0,])
		parmContractStrategy[2]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":2.0E7,"limit":3.0E7,"coveredByReinsurer":1.0,])
		parmContractStrategy[0]=ReinsuranceContractStrategyFactory.getContractStrategy(ReinsuranceContractType.STOPLOSS, ["premiumBase":PremiumBase.ABSOLUTE,"premium":0.0,"attachmentPoint":2.0E7,"limit":3.0E7,"coveredByReinsurer":1.0,])
		parmInuringPriority[2]=0
		parmInuringPriority[0]=0
		parmInuringPriority[1]=0
		parmCoveredLines[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
		parmCoveredLines[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
		parmCoveredLines[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["Covered Lines"], LobMarker)
		parmCoveredPerils[1]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["perils"], PerilMarker)
		parmCoveredPerils[2]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["perils"], PerilMarker)
		parmCoveredPerils[0]=new ComboBoxTableMultiDimensionalParameter(
"""\
[""]
"""
,["perils"], PerilMarker)
	}
	finiteRe {
		parmFractionExperienceAccount[2]=0.75
		parmFractionExperienceAccount[1]=0.75
		parmFractionExperienceAccount[0]=0.75
		parmPremium[0]=3500000.0
		parmPremium[1]=3500000.0
		parmPremium[2]=3500000.0
	}
}
