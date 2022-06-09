package quickstock;

// Author Name     - Soumyadeep Pal
// Author Github   - github.com/realspal
// Author LinkedIn - linkedin.com/in/realspal
// Author Twitter  - twitter.com/realspal
// Author Medium   - soumyadeeppal.medium.com

// Project         - QuickStock Financial Markets Data
// Program Name    - QuickStockReader (Java)
// Function        - Fetches stock market data for 500 NSE Stocks and 100 Cryptocurrencies
// Dependencies    - Jsoup (Java HTML Parser) and classes from java.io and java.util 
// Jsoup Download  - https://jsoup.org/download (jsoup-1.15.1.jar)
// Jsoup Github    - https://github.com/jhy/jsoup/tree/master/src/main/java/org/jsoup (open-source)

//imports
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

//jsoup imports
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//class definition
class QuickStockReader
{
    //global usd to inr conversion rate
    float USDtoINR=75.0f;
    
    //global nifty500 arrays
    String namesNifty500[]={"3M India Ltd.","Aarti Drugs Ltd.","Aarti Industries Ltd.","Aavas Financiers Ltd.","ABB India Ltd.","Abbott India Ltd.","ACC Ltd.","Adani Enterprises Ltd.","Adani Green Energy Ltd.","Adani Ports and Special Economic Zone Ltd.","Adani Total Gas Ltd.","Adani Transmission Ltd.","Aditya Birla Capital Ltd.","Aditya Birla Fashion and Retail Ltd.","Advanced Enzyme Tech Ltd.","Aegis Logistics Ltd.","Affle (India) Ltd.","AIA Engineering Ltd.","Ajanta Pharmaceuticals Ltd.","Alembic Ltd.","Alembic Pharmaceuticals Ltd.","Alkem Laboratories Ltd.","Alkyl Amines Chemicals Ltd.","Alok Industries Ltd.","Amara Raja Batteries Ltd.","Amber Enterprises India Ltd.","Ambuja Cements Ltd.","Angel One Ltd.","Anupam Rasayan India Ltd.","APL Apollo Tubes Ltd.","Apollo Hospitals Enterprise Ltd.","Apollo Tyres Ltd.","Asahi India Glass Ltd.","Ashok Leyland Ltd.","Ashoka Buildcon Ltd.","Asian Paints Ltd.","Aster DM Healthcare Ltd.","Astral Ltd.","AstraZeneca Pharma India Ltd.","Atul Ltd.","AU Small Finance Bank Ltd.","Aurobindo Pharma Ltd.","Avanti Feeds Ltd.","Avenue Supermarts Ltd.","Axis Bank Ltd.","Bajaj Auto Ltd.","Bajaj Consumer Care Ltd.","Bajaj Electricals Ltd","Bajaj Finance Ltd.","Bajaj Finserv Ltd.","Bajaj Holdings & Investment Ltd.","Balaji Amines Ltd.","Balkrishna Industries Ltd.","Balrampur Chini Mills Ltd.","Bandhan Bank Ltd.","Bank of Baroda","Bank of India","Bank of Maharashtra","BASF India Ltd.","Bata India Ltd.","Bayer Cropscience Ltd.","BEML Ltd.","Berger Paints India Ltd.","Bharat Dynamics Ltd.","Bharat Electronics Ltd.","Bharat Forge Ltd.","Bharat Heavy Electricals Ltd.","Bharat Petroleum Corporation Ltd.","Bharat Rasayan Ltd.","Bharti Airtel Ltd.","Biocon Ltd.","Birla Corporation Ltd.","Birlasoft Ltd.","Blue Dart Express Ltd.","Blue Star Ltd.","Bombay Burmah Trading Corporation Ltd.","Bosch Ltd.","Brigade Enterprises Ltd.","Britannia Industries Ltd.","BSE Ltd.","Can Fin Homes Ltd.","Canara Bank","Caplin Point Laboratories Ltd.","Capri Global Capital Ltd.","Carborundum Universal Ltd.","Castrol India Ltd.","Central Bank of India","CCL Products (I) Ltd.","Ceat Ltd.","Central Depository Services (India) Ltd.","Century Plyboards (India) Ltd.","Century Textile & Industries Ltd.","Cera Sanitaryware Ltd","CESC Ltd.","CG Power and Industrial Solutions Ltd.","Chalet Hotels Ltd.","Chambal Fertilizers & Chemicals Ltd.","Cholamandalam Investment and Finance Company Ltd.","Cholamandalam Financial Holdings Ltd.","Cipla Ltd.","City Union Bank Ltd.","Coal India Ltd.","Cochin Shipyard Ltd.","Coforge Ltd.","Colgate Palmolive (India) Ltd.","Computer Age Management Services Ltd.","Container Corporation of India Ltd.","Coromandel International Ltd.","CreditAccess Grameen Ltd.","CRISIL Ltd.","Crompton Greaves Consumer Electricals Ltd.","CSB Bank Ltd.","Cummins India Ltd.","Cyient Ltd.","Dabur India Ltd.","Dalmia Bharat Ltd.","DCB Bank Ltd.","DCM Shriram Ltd.","Deepak Nitrite Ltd.","Delta Corp Ltd.","Dhani Services Ltd.","Dhanuka Agritech Ltd.","Dilip Buildcon Ltd.","Divi's Laboratories Ltd.","Dixon Technologies (India) Ltd.","DLF Ltd.","Dr. Lal Path Labs Ltd.","Dr. Reddy's Laboratories Ltd.","E.I.D. Parry (India) Ltd.","eClerx Services Ltd.","Edelweiss Financial Services Ltd.","Eicher Motors Ltd.","EIH Ltd.","Elgi Equipments Ltd.","Emami Ltd.","Endurance Technologies Ltd.","Engineers India Ltd.","EPL Ltd.","Equitas Holdings Ltd.","Equitas Small Finance Bank Ltd.","Eris Lifesciences Ltd.","Escorts Ltd.","Exide Industries Ltd.","FDC Ltd.","Federal Bank Ltd.","Fertilisers and Chemicals Travancore Ltd.","Fine Organic Industries Ltd.","Finolex Cables Ltd.","Finolex Industries Ltd.","Firstsource Solutions Ltd.","Fortis Healthcare Ltd.","Future Retail Ltd.","GAIL (India) Ltd.","Galaxy Surfactants Ltd.","Garware Technical Fibres Ltd.","General Insurance Corporation of India","Gillette India Ltd.","Gland Pharma Ltd.","Glaxosmithkline Pharmaceuticals Ltd.","Glenmark Pharmaceuticals Ltd.","GMM Pfaudler Ltd.","GMR Infrastructure Ltd.","Godfrey Phillips India Ltd.","Godrej Agrovet Ltd.","Godrej Consumer Products Ltd.","Godrej Industries Ltd.","Godrej Properties Ltd.","Granules India Ltd.","Graphite India Ltd.","Grasim Industries Ltd.","Great Eastern Shipping Co. Ltd.","Greaves Cotton Ltd.","Grindwell Norton Ltd.","Gujarat Alkalies & Chemicals Ltd.","Gujarat Ambuja Exports Ltd.","Gujarat Fluorochemicals Ltd.","Gujarat Gas Ltd.","Gujarat Narmada Valley Fertilizers and Chemicals Ltd.","Gujarat Pipavav Port Ltd.","Gujarat State Fertilizers & Chemicals Ltd.","Gujarat State Petronet Ltd.","Happiest Minds Technologies Ltd.","Hathway Cable & Datacom Ltd.","Hatsun Agro Product Ltd.","Havells India Ltd.","HCL Technologies Ltd.","HDFC Asset Management Company Ltd.","HDFC Bank Ltd.","HDFC Life Insurance Company Ltd.","H.E.G. Ltd.","HeidelbergCement India Ltd.","Hemisphere Properties India Ltd.","Hero MotoCorp Ltd.","HFCL Ltd.","Hikal Ltd.","Hindalco Industries Ltd.","Hinduja Global Solutions Ltd.","Hindustan Aeronautics Ltd.","Hindustan Copper Ltd.","Hindustan Petroleum Corporation Ltd.","Hindustan Unilever Ltd.","Hindustan Zinc Ltd.","Hitachi Energy India Ltd.","Home First Finance Company India Ltd.","Honeywell Automation India Ltd.","Housing Development Finance Corporation Ltd.","Housing & Urban Development Corporation Ltd.","ICICI Bank Ltd.","ICICI Lombard General Insurance Company Ltd.","ICICI Prudential Life Insurance Company Ltd.","ICICI Securities Ltd.","IDBI Bank Ltd.","IDFC Ltd.","IDFC First Bank Ltd.","IFB Industries Ltd.","IIFL Finance Ltd.","IIFL Wealth Management Ltd.","India Cements Ltd.","Indiabulls Housing Finance Ltd.","Indiabulls Real Estate Ltd.","Indiamart Intermesh Ltd.","Indian Bank","Indian Energy Exchange Ltd.","Indian Hotels Co. Ltd.","Indian Oil Corporation Ltd.","Indian Overseas Bank","Indian Railway Finance Corporation Ltd.","Indian Railway Catering And Tourism Corporation Ltd.","Indigo Paints Ltd.","Indo Count Industries Ltd.","Indoco Remedies Ltd.","Indraprastha Gas Ltd.","Indus Towers Ltd.","IndusInd Bank Ltd.","Infibeam Avenues Ltd.","Info Edge (India) Ltd.","Infosys Ltd.","Ingersoll Rand India Ltd.","Inox Leisure Ltd.","Intellect Design Arena Ltd.","InterGlobe Aviation Ltd.","IOL Chemicals and Pharmaceuticals Ltd.","Ipca Laboratories Ltd.","IRB Infrastructure Developers Ltd.","IRCON International Ltd.","ITC Ltd.","ITI Ltd.","J.B. Chemicals & Pharmaceuticals Ltd.","J.K. Cement Ltd.","Jamna Auto Industries Ltd.","Jindal Saw Ltd.","Jindal Stainless Hisar Ltd.","Jindal Stainless Ltd.","Jindal Steel & Power Ltd.","JK Lakshmi Cement Ltd.","JK Paper Ltd.","JK Tyre & Industries Ltd.","JM Financial Ltd.","Johnson Controls-Hitachi AC India Ltd","JSW Energy Ltd.","JSW Steel Ltd.","Jubilant Foodworks Ltd.","Jubilant Ingrevia Ltd.","Jubilant Pharmova Ltd.","Justdial Ltd.","Jyothy Labs Ltd.","Kajaria Ceramics Ltd.","Kalpataru Power Transmission Ltd.","Kalyan Jewellers India Ltd.","Kansai Nerolac Paints Ltd.","Karur Vysya Bank Ltd.","Kaveri Seed Company Ltd.","Kec International Ltd.","KEI Industries Ltd.","KNR Constructions Ltd.","Kotak Mahindra Bank Ltd.","KPIT Technologies Ltd.","KPR Mill Ltd.","KRBL Ltd.","KSB Ltd.","L&T Finance Holdings Ltd.","L&T Technology Services Ltd.","La Opala RG Ltd.","Lakshmi Machine Works Ltd.","Larsen & Toubro Ltd.","Larsen & Toubro Infotech Ltd.","Laurus Labs Ltd.","Laxmi Organic Industries Ltd.","Lemon Tree Hotels Ltd.","LIC Housing Finance Ltd.","Linde India Ltd.","Lupin Ltd.","Lux Industries Ltd.","MRF Ltd.","Macrotech Developers Ltd.","Mahanagar Gas Ltd.","Mahindra & Mahindra Ltd.","Mahindra & Mahindra Financial Services Ltd.","Mahindra CIE Automotive Ltd.","Mahindra Holidays & Resorts India Ltd.","Mahindra Logistics Ltd.","Manappuram Finance Ltd.","Mangalore Refinery & Petrochemicals Ltd.","Marico Ltd.","Maruti Suzuki India Ltd.","Mastek Ltd.","Max Financial Services Ltd.","Max Healthcare Institute Ltd.","Mazagon Dock Shipbuilders Ltd.","Metropolis Healthcare Ltd.","Minda Corp Ltd.","Minda Industries Ltd.","MindTree Ltd.","Mishra Dhatu Nigam Ltd.","MMTC Ltd.","MOIL Ltd.","Motilal Oswal Financial Services Ltd.","MphasiS Ltd.","Multi Commodity Exchange of India Ltd.","Muthoot Finance Ltd.","Narayana Hrudayalaya Ltd.","NATCO Pharma Ltd.","National Aluminium Co. Ltd.","National Fertilizers Ltd.","Navin Fluorine International Ltd.","Nazara Technologies Ltd.","NBCC (India) Ltd.","NCC Ltd.","NESCO Ltd.","Nestle India Ltd.","Network18 Media & Investments Ltd.","New India Assurance Company Ltd.","NHPC Ltd.","Nilkamal Ltd.","Nippon Life India Asset Management Ltd.","NLC India Ltd.","NMDC Ltd.","NOCIL Ltd.","NTPC Ltd.","Oberoi Realty Ltd.","Oil & Natural Gas Corporation Ltd.","Oil India Ltd.","Oracle Financial Services Software Ltd.","Orient Electric Ltd.","Page Industries Ltd.","PCBL Ltd.","Persistent Systems Ltd.","Petronet LNG Ltd.","Pfizer Ltd.","PI Industries Ltd.","Pidilite Industries Ltd.","Piramal Enterprises Ltd.","PNB Housing Finance Ltd.","PNC Infratech Ltd.","Poly Medicure Ltd.","Polycab India Ltd.","Polyplex Corporation Ltd.","Poonawalla Fincorp Ltd.","Power Finance Corporation Ltd.","Power Grid Corporation of India Ltd.","Praj Industries Ltd.","Prestige Estates Projects Ltd.","Prince Pipes and Fittings Ltd.","Prism Johnson Ltd.","Procter & Gamble Health Ltd.","Procter & Gamble Hygiene & Health Care Ltd.","Punjab National Bank","PVR Ltd.","Quess Corp Ltd.","Radico Khaitan Ltd","Rail Vikas Nigam Ltd.","Railtel Corporation Of India Ltd.","Rain Industries Ltd","Rajesh Exports Ltd.","Rallis India Ltd.","Rashtriya Chemicals & Fertilizers Ltd.","Ratnamani Metals & Tubes Ltd.","RBL Bank Ltd.","REC Ltd.","Redington (India) Ltd.","Relaxo Footwears Ltd.","Reliance Industries Ltd.","Restaurant Brands Asia Ltd.","RHI MAGNESITA INDIA LTD.","RITES Ltd.","Rossari Biotech Ltd.","Route Mobile Ltd.","Sanofi India Ltd.","State Bank of India","SBI Cards and Payment Services Ltd.","SBI Life Insurance Company Ltd.","Schaeffler India Ltd.","Schneider Electric Infrastructure Ltd.","Sequent Scientific Ltd.","Sharda Cropchem Ltd.","Sheela Foam Ltd.","Shilpa Medicare Ltd.","Shipping Corporation of India Ltd.","Shree Cement Ltd.","Shriram Transport Finance Co. Ltd.","Shriram City Union Finance Ltd.","Siemens Ltd.","SIS Ltd.","SJVN Ltd.","SKF India Ltd.","Sobha Ltd.","Solar Industries India Ltd.","Solara Active Pharma Sciences Ltd.","Sonata Software Ltd.","Spandana Sphoorty Financial Ltd.","Spicejet Ltd.","SRF Ltd.","Star Cement Ltd.","Steel Authority of India Ltd.","Sterlite Technologies Ltd.","Strides Pharma Science Ltd.","Sudarshan Chemical Industries Ltd.","Sumitomo Chemical India Ltd.","Sun Pharmaceutical Industries Ltd.","Sun Pharma Advanced Research Company Ltd.","Sun TV Network Ltd.","Sundaram Finance Ltd.","Sundram Fasteners Ltd.","Sunteck Realty Ltd.","Suprajit Engineering Ltd.","Supreme Industries Ltd.","Supreme Petrochem Ltd.","Suven Pharmaceuticals Ltd.","Suzlon Energy Ltd.","Symphony Ltd.","Syngene International Ltd.","Tanla Platforms Ltd.","Tasty Bite Eatables Ltd.","Tata Chemicals Ltd.","Tata Coffee Ltd.","Tata Communications Ltd.","Tata Consultancy Services Ltd.","Tata Consumer Products Ltd.","Tata Elxsi Ltd.","Tata Motors Ltd.","Tata Motors Ltd. DVR","Tata Power Co. Ltd.","Tata Steel Ltd.","Tata Steel Long Products Ltd.","Tata Teleservices (Maharashtra) Ltd.","TCI Express Ltd.","TCNS Clothing Co. Ltd.","Teamlease Services Ltd.","Tech Mahindra Ltd.","Phoenix Mills Ltd.","The Ramco Cements Ltd.","Thermax Ltd.","Thyrocare Technologies Ltd.","Timken India Ltd.","Titan Company Ltd.","Torrent Pharmaceuticals Ltd.","Torrent Power Ltd.","Trent Ltd.","Trident Ltd.","Triveni Turbine Ltd.","TTK Prestige Ltd.","Tube Investments of India Ltd.","TV18 Broadcast Ltd.","TVS Motor Company Ltd.","UCO Bank","UFLEX Ltd.","Ujjivan Financial Services Ltd.","Ujjivan Small Finance Bank Ltd.","UltraTech Cement Ltd.","Union Bank of India","United Breweries Ltd.","United Spirits Ltd.","UPL Ltd.","UTI Asset Management Company Ltd.","V-Guard Industries Ltd.","V-Mart Retail Ltd.","Vaibhav Global Ltd.","Vakrangee Ltd.","Vardhman Textiles Ltd.","Varroc Engineering Ltd.","Varun Beverages Ltd.","Vedanta Ltd.","Venky's (India) Ltd.","Vinati Organics Ltd.","VIP Industries Ltd.","Vodafone Idea Ltd.","Voltas Ltd.","Welspun India Ltd.","Welspun Corp Ltd.","Westlife Development Ltd.","Whirlpool of India Ltd.","Wipro Ltd.","Wockhardt Ltd.","Yes Bank Ltd.","Zee Entertainment Enterprises Ltd.","Zensar Technolgies Ltd.","ZF Commercial Vehicle Control Systems India Ltd.","Zydus Lifesciences Ltd.","Zydus Wellness Ltd."};
    String symbolsNifty500[]={"3MINDIA","AARTIDRUGS","AARTIIND","AAVAS","ABB","ABBOTINDIA","ACC","ADANIENT","ADANIGREEN","ADANIPORTS","ATGL","ADANITRANS","ABCAPITAL","ABFRL","ADVENZYMES","AEGISCHEM","AFFLE","AIAENG","AJANTPHARM","ALEMBICLTD","APLLTD","ALKEM","ALKYLAMINE","ALOKINDS","AMARAJABAT","AMBER","AMBUJACEM","ANGELONE","ANURAS","APLAPOLLO","APOLLOHOSP","APOLLOTYRE","ASAHIINDIA","ASHOKLEY","ASHOKA","ASIANPAINT","ASTERDM","ASTRAL","ASTRAZEN","ATUL","AUBANK","AUROPHARMA","AVANTIFEED","DMART","AXISBANK","BAJAJ-AUTO","BAJAJCON","BAJAJELEC","BAJFINANCE","BAJAJFINSV","BAJAJHLDNG","BALAMINES","BALKRISIND","BALRAMCHIN","BANDHANBNK","BANKBARODA","BANKINDIA","MAHABANK","BASF","BATAINDIA","BAYERCROP","BEML","BERGEPAINT","BDL","BEL","BHARATFORG","BHEL","BPCL","BHARATRAS","BHARTIARTL","BIOCON","BIRLACORPN","BSOFT","BLUEDART","BLUESTARCO","BBTC","BOSCHLTD","BRIGADE","BRITANNIA","BSE","CANFINHOME","CANBK","CAPLIPOINT","CGCL","CARBORUNIV","CASTROLIND","CENTRALBK","CCL","CEATLTD","CDSL","CENTURYPLY","CENTURYTEX","CERA","CESC","CGPOWER","CHALET","CHAMBLFERT","CHOLAFIN","CHOLAHLDNG","CIPLA","CUB","COALINDIA","COCHINSHIP","COFORGE","COLPAL","CAMS","CONCOR","COROMANDEL","CREDITACC","CRISIL","CROMPTON","CSBBANK","CUMMINSIND","CYIENT","DABUR","DALBHARAT","DCBBANK","DCMSHRIRAM","DEEPAKNTR","DELTACORP","DHANI","DHANUKA","DBL","DIVISLAB","DIXON","DLF","LALPATHLAB","DRREDDY","EIDPARRY","ECLERX","EDELWEISS","EICHERMOT","EIHOTEL","ELGIEQUIP","EMAMILTD","ENDURANCE","ENGINERSIN","EPL","EQUITAS","EQUITASBNK","ERIS","ESCORTS","EXIDEIND","FDC","FEDERALBNK","FACT","FINEORG","FINCABLES","FINPIPE","FSL","FORTIS","FRETAIL","GAIL","GALAXYSURF","GARFIBRES","GICRE","GILLETTE","GLAND","GLAXO","GLENMARK","GMMPFAUDLR","GMRINFRA","GODFRYPHLP","GODREJAGRO","GODREJCP","GODREJIND","GODREJPROP","GRANULES","GRAPHITE","GRASIM","GESHIP","GREAVESCOT","GRINDWELL","GUJALKALI","GAEL","FLUOROCHEM","GUJGASLTD","GNFC","GPPL","GSFC","GSPL","HAPPSTMNDS","HATHWAY","HATSUN","HAVELLS","HCLTECH","HDFCAMC","HDFCBANK","HDFCLIFE","HEG","HEIDELBERG","HEMIPROP","HEROMOTOCO","HFCL","HIKAL","HINDALCO","HGS","HAL","HINDCOPPER","HINDPETRO","HINDUNILVR","HINDZINC","POWERINDIA","HOMEFIRST","HONAUT","HDFC","HUDCO","ICICIBANK","ICICIGI","ICICIPRULI","ISEC","IDBI","IDFC","IDFCFIRSTB","IFBIND","IIFL","IIFLWAM","INDIACEM","IBULHSGFIN","IBREALEST","INDIAMART","INDIANB","IEX","INDHOTEL","IOC","IOB","IRFC","IRCTC","INDIGOPNTS","ICIL","INDOCO","IGL","INDUSTOWER","INDUSINDBK","INFIBEAM","NAUKRI","INFY","INGERRAND","INOXLEISUR","INTELLECT","INDIGO","IOLCP","IPCALAB","IRB","IRCON","ITC","ITI","JBCHEPHARM","JKCEMENT","JAMNAAUTO","JINDALSAW","JSLHISAR","JSL","JINDALSTEL","JKLAKSHMI","JKPAPER","JKTYRE","JMFINANCIL","JCHAC","JSWENERGY","JSWSTEEL","JUBLFOOD","JUBLINGREA","JUBLPHARMA","JUSTDIAL","JYOTHYLAB","KAJARIACER","KALPATPOWR","KALYANKJIL","KANSAINER","KARURVYSYA","KSCL","KEC","KEI","KNRCON","KOTAKBANK","KPITTECH","KPRMILL","KRBL","KSB","L&TFH","LTTS","LAOPALA","LAXMIMACH","LT","LTI","LAURUSLABS","LXCHEM","LEMONTREE","LICHSGFIN","LINDEINDIA","LUPIN","LUXIND","MRF","LODHA","MGL","M&M","M&MFIN","MAHINDCIE","MHRIL","MAHLOG","MANAPPURAM","MRPL","MARICO","MARUTI","MASTEK","MFSL","MAXHEALTH","MAZDOCK","METROPOLIS","MINDACORP","MINDAIND","MINDTREE","MIDHANI","MMTC","MOIL","MOTILALOFS","MPHASIS","MCX","MUTHOOTFIN","NH","NATCOPHARM","NATIONALUM","NFL","NAVINFLUOR","NAZARA","NBCC","NCC","NESCO","NESTLEIND","NETWORK18","NIACL","NHPC","NILKAMAL","NAM-INDIA","NLCINDIA","NMDC","NOCIL","NTPC","OBEROIRLTY","ONGC","OIL","OFSS","ORIENTELEC","PAGEIND","PCBL","PERSISTENT","PETRONET","PFIZER","PIIND","PIDILITIND","PEL","PNBHOUSING","PNCINFRA","POLYMED","POLYCAB","POLYPLEX","POONAWALLA","PFC","POWERGRID","PRAJIND","PRESTIGE","PRINCEPIPE","PRSMJOHNSN","PGHL","PGHH","PNB","PVR","QUESS","RADICO","RVNL","RAILTEL","RAIN","RAJESHEXPO","RALLIS","RCF","RATNAMANI","RBLBANK","RECLTD","REDINGTON","RELAXO","RELIANCE","RBA","RHIM","RITES","ROSSARI","ROUTE","SANOFI","SBIN","SBICARD","SBILIFE","SCHAEFFLER","SCHNEIDER","SEQUENT","SHARDACROP","SFL","SHILPAMED","SCI","SHREECEM","SRTRANSFIN","SHRIRAMCIT","SIEMENS","SIS","SJVN","SKFINDIA","SOBHA","SOLARINDS","SOLARA","SONATSOFTW","SPANDANA","SPICEJET","SRF","STARCEMENT","SAIL","STLTECH","STAR","SUDARSCHEM","SUMICHEM","SUNPHARMA","SPARC","SUNTV","SUNDARMFIN","SUNDRMFAST","SUNTECK","SUPRAJIT","SUPREMEIND","SPLPETRO","SUVENPHAR","SUZLON","SYMPHONY","SYNGENE","TANLA","TASTYBITE","TATACHEM","TATACOFFEE","TATACOMM","TCS","TATACONSUM","TATAELXSI","TATAMOTORS","TATAMTRDVR","TATAPOWER","TATASTEEL","TATASTLLP","TTML","TCIEXP","TCNSBRANDS","TEAMLEASE","TECHM","PHOENIXLTD","RAMCOCEM","THERMAX","THYROCARE","TIMKEN","TITAN","TORNTPHARM","TORNTPOWER","TRENT","TRIDENT","TRITURBINE","TTKPRESTIG","TIINDIA","TV18BRDCST","TVSMOTOR","UCOBANK","UFLEX","UJJIVAN","UJJIVANSFB","ULTRACEMCO","UNIONBANK","UBL","MCDOWELL-N","UPL","UTIAMC","VGUARD","VMART","VAIBHAVGBL","VAKRANGEE","VTL","VARROC","VBL","VEDL","VENKEYS","VINATIORGA","VIPIND","IDEA","VOLTAS","WELSPUNIND","WELCORP","WESTLIFE","WHIRLPOOL","WIPRO","WOCKPHARMA","YESBANK","ZEEL","ZENSARTECH","ZFCVINDIA","ZYDUSLIFE","ZYDUSWELL"};
    String lookupNifty500[]={"3M India","Aarti Drugs Ltd","Aarti Industries","Aavas Financiers","ABB India","Abbott India","ACC","Adani Enterprises","Adani Green Energy","Adani Ports & SEZ","Adani Total Gas","Adani Transmission Ltd","Aditya Birla Capital","Aditya Birla Fashion","Advanced Enzyme Tech","Aegis Logistics Ltd","Affle India","AIA Engineering","Ajanta Pharma Ltd","Alembic Ltd","Alembic Pharmaceuticals Ltd","Alkem Laboratories Ltd","Alkyl Amines Chemicals Ltd","Alok Industries","Amara Raja Batteries","Amber Enterprises India","Ambuja Cements","Angel One","Anupam Rasayan India","APL Apollo Tubes Ltd","Apollo Hospitals","Apollo Tyres","Asahi India Glass","Ashok Leyland","Ashoka Buildcon Ltd","Asian Paints","Aster DM Healthcare","Astral Ltd","AstraZeneca Pharma","Atul","AU Small Finance Bank","Aurobindo Pharma","Avanti Feeds","Avenue Supermarts","AXIS Bank","Bajaj Auto","Bajaj Consumer Care","Bajaj Electricals","Bajaj Finance","Bajaj Finserv","Bajaj Holdings","Balaji Amines Ltd","Balkrishna Industries","Balrampur Chini Mills","Bandhan Bank","Bank Of Baroda","Bank of India","Bank of maharashtra","BASF India","Bata India","Bayer Cropscience","BEML","Berger Paints","Bharat Dynamics","Bharat Electronics","Bharat Forge","Bharat Heavy Electricals","Bharat Petroleum","Bharat Rasayan Ltd","Bharti Airtel","Biocon","Birla Corporation","Birlasoft","Blue Dart Express","Blue Star","Bombay Burmah Trading","Bosch","Brigade Enterprises","Britannia Industries","BSE","Can Fin Homes","Canara Bank","Caplin Point Laboratories Ltd","Capri Global Capital","Carborundum Universal","Castrol India","CBI","CCL Products India Ltd","CEAT Ltd","Central Depository Services","Century Plyboards","Century Textiles","Cera Sanitaryware Ltd","CESC","CG Power and Industrial Solutions","Chalet Hotels","Chambal Fertilisers","Cholamandalam","Cholamandalam Financial","Cipla","City Union Bank","Coal India","Cochin Shipyard","Coforge","Colgate-Palmolive India","Computer Age","Container India","Coromandel Int","CreditAccess Grameen","CRISIL","Crompton Greaves Consumer Electricals L","Csb Bank","Cummins India Ltd","Cyient Ltd-B","Dabur India","Dalmia Bharat B","DCB Bank","DCM Shriram","Deepak Nitrite Ltd","Delta Corp","Dhani Services","Dhanuka Agritech Ltd","Dilip Buildcon","Divi's Labs","Dixon Tech","DLF","Dr Lal PathLabs Ltd","Dr. Reddy’s Labs","E.I.D-Parry","eClerx Services","Edelweiss Financial","Eicher Motors","EIH","Elgi Equipments","Emami","Endurance Technologies Cn","Engineers India","EPL","Equitas Holdings","Equitas Small Finance","Eris Lifesciences","Escorts","Exide Industries","FDC","Federal Bank","Fertilisers Chemicals Travancore","Fine Organic","Finolex Cables","Finolex Industries","Firstsource Solutions","Fortis Healthcare","Future Retail","GAIL Ltd","Galaxy Surfactants","Garware Wall Ropes Ltd","General Insurance India","Gillette India","Gland","GlaxoSmithkline Pharma","Glenmark Pharma","GMM Pfaudler","GMR Infrastructure","Godfrey Phillips India","Godrej Agrovet","Godrej Consumer","Godrej Industries","Godrej Properties","Granules India","Graphite India","Grasim Industries","Great Eastern Shipping","Greaves Cotton","Grindwell Norton Ltd","Gujarat Alkalis&Chemicals","Gujarat Ambuja Exports","Gujarat Fluorochemicals","Gujarat Gas","Gujarat Narmada Valley Fert.","Gujarat Pipavav Port","Gujarat State Fertilizers","Gujarat State Petronet","Happiest Minds","Hathway Cable","Hatsun Agro Product Ltd","Havells India","HCL Tech","HDFC Asset Management","HDFC Bank","HDFC Life","HEG","HeidelbergCement India","Hemisphere Properties India","Hero MotoCorp","HFCL","Hikal Ltd","Hindalco Industries","Hinduja Global Solutions Ltd","Hindustan Aeronautics","Hindustan Copper","Hindustan Petroleum","Hindustan Unilever","Hindustan Zinc","Hitachi Energy India","Home First Finance","Honeywell Automation","Housing Development Finance","Housing Urban Develop","ICICI Bank","ICICI Lombard","ICICI Prudential Life Insurance","ICICI Securities","IDBI Bank","IDFC","IDFC First Bank","IFB Industries Ltd","IIFL Fiinance B","Iifl Wealth","India Cements","Indiabulls","Indiabulls RE","IndiaMART InterMESH","Indian Bank","Indian Energy Exchange","Indian Hotels Co.","Indian Oil","Indian Overseas Bank","Indian Railway","Indian Railway Catering","Indigo Paints","Indo Count Industries","Indoco Remedies Ltd","Indraprastha Gas","Indus Towers","IndusInd Bank","Infibeam Avenues","Info Edge India","Infosys","Ingersoll-Rand IN","Inox Leisure","Intellect Design Arena","Interglobe Aviation Ltd","IOL Chemicals Pharmaceuticals","Ipca Laboratories","IRB Infrastructure","Ircon","ITC","ITI Ltd","J.B. Chemicals&Pharma","J.K. Cement","Jamna Auto","Jindal Saw","Jindal Stainless","Jindal Stainless","Jindal Steel&Power","JK Lakshmi Cement","JK Paper Ltd","JK Tyre&Industries","JM Financial","Johnson Controls-Hitachi Air","JSW Energy","JSW Steel","Jubilant Foodworks","Jubilant Ingrevia","Jubilant Pharmova","Just Dial","Jyothy Labs","Kajaria Ceramics","Kalpataru Power","Kalyan Jewellers India Pvt","Kansai Nerolac Paints","Karur Vysya Bank","Kaveri Seed","KEC International","KEI Industries Ltd","KNR Constructions","Kotak Mahindra Bank","KPIT Tech","KPR Mill","KRBL Ltd","KSB","L&T Finance Hld","L&T Technology Services","La Opala R G Ltd","Lakshmi Machine Works","Larsen & Toubro","Larsen & Toubro Infotech","Laurus Labs","Laxmi Organic Co","Lemon Tree Hotels","Lic Housing Finance","Linde India","Lupin","Lux Industries Ltd","M.R.F.","Macrotech Developers","Mahanagar Gas","Mahindra & Mahindra","Mahindra & Mahindra Financial","Mahindra CIE Automotive Ltd","Mahindra Holidays","Mahindra Logistics","Manappuram Finance","Mangalore Refinery","Marico","Maruti Suzuki","Mastek","Max Financial","Max Healthcare Institute","Mazagon Dock","Metropolis Healthcare","Minda Corp","Minda Industries","MindTree","Mishra Dhatu Nigam","MMTC","MOIL","Motilal Oswal","Mphasis","Multi Commodity Exchange","Muthoot Finance","Narayana Hrudayalaya","Natco Pharma","National Aluminum Co.","National Fertilizers","Navin Fluorine","Nazara Technologies","NBCC India","NCC","Nesco","Nestle India","Network","New India Assurance","NHPC","Nilkamal","Nippon India","NLC India","NMDC","Nocil Ltd","NTPC","Oberoi Realty","Oil & Natural Gas","Oil India","Oracle","Orient Electric","Page Industries","PCBL Ltd","Persistent Systems","Petronet LNG","Pfizer Ltd","PI Industries","Pidilite Industries","Piramal Enterprises","PNB Housing Finance","PNC Infratech","Poly Medicure Ltd","Polycab India","Polyplex","Poonawalla Fincorp","Power Finance","Power Grid","Praj Industries","Prestige Estates","Prince Pipes And Fittings","Prism Johnson","Procter & Gamble Health","Procter & Gamble Hygiene","Punjab National Bank","PVR","Quess Corp","Radico Khaitan","Rail Vikas Nigam","Railtel","Rain Industries","Rajesh Exports","Rallis India","Rashtriya Chemicals","Ratnamani Metals Tubes Ltd","RBL Bank","REC","Redington India","Relaxo Footwears Ltd","Reliance Industries","Restaurant Brands Asia","RHI Magnesita India","RITES","Rossari Biotech","Route Mobile","Sanofi India","SBI","SBI Cards","SBI Life Insurance","Schaeffler India","Schneider Electric Infrastructure","SeQuent Scientific","Sharda Cropchem Ltd","Sheela Foam","Shilpa Medicare Ltd","Shipping Corporation","Shree Cements","Shriram Transport","Shriram-City Union","Siemens Ltd","SIS","SJVN","SKF India","Sobha Developers","Solar Industries India Ltd","Solara Active Pharma","Sonata Software","Spandana Sphoorty Financial Ltd BO","Spicejet","SRF","Star Cement","Steel Authority","Sterlite Technologies","Strides Pharma","Sudarshan Chemical Industries","Sumitomo Chemical","Sun Pharma","Sun Pharma Adv. Research","Sun TV Network Ltd","Sundaram Finance","Sundram Fasteners","Sunteck Realty","Suprajit Engineering Ltd","Supreme Industries","Supreme Petrochem","Suven Pharma","Suzlon Energy","Symphony Ltd","Syngene International Ltd","Tanla Platforms","Tasty Bite Eatables","Tata Chemicals","Tata Coffee","Tata Communications","Tata Consultancy","Tata Consumer Products","Tata Elxsi","Tata Motors","Tata Motors DV Ltd","Tata Power Co.","Tata Steel Ltd","Tata Steel Prod","Tata Teleservices","TCI Express","TCNS Clothing","Teamlease Services","Tech Mahindra","The Phoenix Mills","The Ramco Cements","Thermax","Thyrocare Technologies Ltd","Timken India","Titan Company","Torrent Pharma","Torrent Power Ltd","Trent","Trident","Triveni Turbine","TTK Prestige","Tube Invest India","TV18 Broadcast","TVS Motor Company","UCO Bank","Uflex","Ujjivan Financial Services Ltd","Ujjivan Small Fin","UltraTech Cement","Union Bank of India","United Breweries Ltd","United Spirits","UPL","Uti Asset","V Guard Industries","V Mart Retail Ltd","Vaibhav Global","Vakrangee Softwares","Vardhman Textiles","Varroc Engineering","Varun Beverages","Vedanta","Venkys India","Vinati Organics Ltd","VIP Industries","Vodafone Idea","Voltas","Welspun","Welspun Corp","Westlife Development Ltd-BO","Whirlpool of India","Wipro","Wockhardt","Yes Bank","Zee Entertainment","Zensar Tech","ZF Commercial Vehicle Control Systems I","Zydus Lifesciences","Zydus Wellness"};
    
    //global nifty500 hashmaps
    HashMap<String,Integer> symToId;                //symbol -> id
    HashMap<Integer,ArrayList<String>> idToInfo;    //id -> symbol,name
    HashMap<Integer,ArrayList<String>> idToStat;    //id -> LTP,High,Low,Chg,ChgP,Vol
    HashMap<String,Integer> lookToId;               //(lookup only) name -> id
    
    //global crypto100 arrays
    String namesCrypto100[]={"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"};
    String symbolsCrypto100[]={"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-"};
        
    //global crypto100 hashmaps
    HashMap<String,Integer> cryptoSymToId;              //symbol -> id
    HashMap<Integer,ArrayList<String>> cryptoIdToInfo;  //id -> symbol,name
    HashMap<Integer,ArrayList<String>> cryptoIdToStat;  //id -> LTP,High(-),Low(-),Chg(-),ChgP,Vol
    
    //constructor
    QuickStockReader()
    {
        symToId=new HashMap<String,Integer>();
        idToInfo=new HashMap<Integer,ArrayList<String>>();
        idToStat=new HashMap<Integer,ArrayList<String>>();
        lookToId=new HashMap<String,Integer>();
        
        cryptoSymToId=new HashMap<String,Integer>();
        cryptoIdToInfo=new HashMap<Integer,ArrayList<String>>();
        cryptoIdToStat=new HashMap<Integer,ArrayList<String>>();
        
        this.initialise();
    }
    
    //initialise
    public void initialise()
    {
        int id;
        for(int i=0;i<499;i++)
        {
            id=i+1;
            symToId.put(symbolsNifty500[i],id);
            idToInfo.put(id,new ArrayList(Arrays.asList(symbolsNifty500[i],namesNifty500[i])));
            idToStat.put(id,new ArrayList(Arrays.asList("-","-","-","-","-","-")));
            lookToId.put(lookupNifty500[i],id);
        }
        for(int i=0;i<100;i++)
        {
            id=i+1;
            cryptoSymToId.put("-",id);
            cryptoIdToInfo.put(id,new ArrayList(Arrays.asList("-","-")));
            cryptoIdToStat.put(id,new ArrayList(Arrays.asList("-","-","-","-","-","-")));
        }
    }
    
    //convert USD to INR
    public String convertUSDtoINR(String usd)
    {
        float f=0.0f;
        try
        {
            f=Float.parseFloat(usd.replace(",",""));
        }
        catch(Exception ex)
        {
            //do nothing
        }
        f*=USDtoINR;
        String inr=""+f;
        if(inr.length()-inr.indexOf(".")==2)
            inr=inr+"0";
        return inr;
    }
    
    //fetch USD to INR conversion rate
    public void fetchUSDtoINR()throws IOException
    {
        String url="https://www.investing.com/currencies/usd-inr";
        Document doc=Jsoup.connect(url).timeout(8000).get();
        String s=doc.select("div.instrument-price_instrument-price__3uw25 span.text-2xl").first().text();
        try
        {
            USDtoINR=Float.parseFloat(s);
        }
        catch(Exception ex)
        {
            //do nothing
        }
    }
        
    //refresh nifty500 stats
    public void fetchNifty500()throws IOException
    {
        String url="https://www.investing.com/indices/s-p-cnx-500-components";
        Document doc=Jsoup.connect(url).timeout(8000).get();
        Elements ele=doc.select("table.genTbl tbody tr");
        int k=0,i=0,id=0;
        String line="",str="";
        char ch1='\0',ch2='\0';
        String stats[];
        ArrayList<String> arr;
        for(Element e:ele)
        {
            if(k>=499)
            break;
            
            str=e.select("td.bold").first().text();
            if(!lookToId.containsKey(str))
            {
                continue;
            }
            id=lookToId.get(str);
            
            line=e.text();
            ch1=line.charAt(0);
            for(i=1;i<60;i++)
            {
                ch2=line.charAt(i);
                if(ch1==' ' && Character.isDigit(ch2))
                break;
                ch1=ch2;
            }
            line=line.substring(i);
            stats=line.split(" ",7);
            
            //id=k+1;
            arr=idToStat.get(id);
            arr.clear();
            arr.add(stats[0]);
            arr.add(stats[1]);
            arr.add(stats[2]);
            arr.add(stats[3]);
            arr.add(stats[4]);
            arr.add(stats[5]);
            
            k++;
        }            
    }
    
    //refresh crypto100 info and stats
    public void fetchCrypto100()throws IOException
    {
        String url="https://www.investing.com/crypto/currencies";
        Document doc=Jsoup.connect(url).timeout(8000).get();
        Elements ele=doc.select("table.genTbl tbody tr");
        int k=0;
        ArrayList<String> arr;
        String str="",str2="";
        cryptoSymToId.clear();
        cryptoIdToInfo.clear();
        for(Element e:ele)
        {
            if(k>=100)
            break;
            
            str=e.select("td.js-currency-name").first().text();
            str2=e.select("td.js-currency-symbol").first().text();            
            namesCrypto100[k]=str;
            symbolsCrypto100[k]=str2;            
            cryptoSymToId.put(str2,k+1);            
            cryptoIdToInfo.put(k+1,new ArrayList(Arrays.asList(str2,str)));
            
            arr=cryptoIdToStat.get(k+1);            
            arr.set(0,convertUSDtoINR(e.select("td.js-currency-price").first().text()));
            arr.set(4,e.select("td.js-currency-change-24h").first().text());
            arr.set(5,e.select("td.js-24h-volume").first().text());
            cryptoIdToStat.put(k+1,arr);
            
            k++;
        }            
    }
    
    //refresh nifty500 and crypto100 stats
    public void refreshData()throws IOException
    {
        fetchNifty500();
        fetchCrypto100();
    }
    
    //get stock data (info and stats) as string
    public String getStockData(String stockSymbol)
    {
        String str="data not available";
        if(!symToId.containsKey(stockSymbol))
        return str;
        int id=symToId.get(stockSymbol);
        ArrayList<String> a=new ArrayList<String>();
        a=idToInfo.get(id);
        str="[ "+a.get(0)+" ("+a.get(1)+") : ";
        a=idToStat.get(id);
        str=str+"LTP=₹"+a.get(0)+",High=₹"+a.get(1)+",Low=₹"+a.get(2)+",Chg="+a.get(3)+",Chg%="+a.get(4)+",Vol="+a.get(5)+" ]";
        return str;
    }
    
    //get stock data (info and stats) as string
    public String getCryptoData(String cryptoSymbol)
    {
        String str="data not available";
        if(!cryptoSymToId.containsKey(cryptoSymbol))
        return str;
        int id=cryptoSymToId.get(cryptoSymbol);
        ArrayList<String> a=new ArrayList<String>();
        a=cryptoIdToInfo.get(id);
        str="[ "+a.get(0)+" ("+a.get(1)+") : ";
        a=cryptoIdToStat.get(id);
        str=str+"LTP=₹"+a.get(0)+",Chg%="+a.get(4)+",Vol="+a.get(5)+" ]";
        return str;
    }
    
    //main function for testing
    public static void main(String args[])throws IOException
    {
        //important calls
        //QuickStockReader(),fetchUSDtoINR(),refreshData(),fetchNifty500(),fetchCrypto100(),
        //getStockData(String stockSymbol),getCryptoData(String cryptoSymbol)
        
        //declarations
        ArrayList<String> a=new ArrayList<String>();
        
        //illustrating QuickStockReader()
        QuickStockReader qsb=new QuickStockReader();
        System.out.println("-------------------------------------------");
        
        //illustrating fetchUSDtoINR()
        qsb.fetchUSDtoINR();
        System.out.println("$ 1 (USD) = ₹ "+qsb.USDtoINR+" (INR)");
        System.out.println("-------------------------------------------");
        
        //illustrating fetchNifty500()
        qsb.fetchNifty500();
        for(int i=1;i<=10;i++)
        {
            a=qsb.idToInfo.get(i);
            System.out.print("INFO = "+a+" and STAT = ");
            a=qsb.idToStat.get(i);
            System.out.println(a);
        }
        System.out.println("-------------------------------------------");
        
        //illustrating fetchCrypto100()
        qsb.fetchCrypto100();
        for(int i=1;i<=10;i++)
        {
            a=qsb.cryptoIdToInfo.get(i);
            System.out.print("INFO = "+a+" and STAT = ");
            a=qsb.cryptoIdToStat.get(i);
            System.out.println(a);
        }
        System.out.println("-------------------------------------------");
        
        //illustrating refreshData()
        qsb.refreshData();
        for(int i=1;i<=5;i++)
        {
            a=qsb.idToInfo.get(i);
            System.out.print("INFO = "+a+" and STAT = ");
            a=qsb.idToStat.get(i);
            System.out.println(a);
        }
        for(int i=1;i<=5;i++)
        {
            a=qsb.cryptoIdToInfo.get(i);
            System.out.print("INFO = "+a+" and STAT = ");
            a=qsb.cryptoIdToStat.get(i);
            System.out.println(a);
        }
        System.out.println("-------------------------------------------");
        
        //illustrating getStockData(String stockSymbol) and getCryptoData(String cryptoSymbol)
        System.out.println("objectName.getStockData(\"RELIANCE\") -->");
        System.out.println(qsb.getStockData("RELIANCE"));
        System.out.println("objectName.getCryptoData(\"BTC\") -->");
        System.out.println(qsb.getCryptoData("BTC"));
        System.out.println("-------------------------------------------");
    }
}
