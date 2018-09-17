package com.xbkj.basic.vo.pub.util;

/**
 * ����ת������ 
 * �������ڣ�(2002-3-12 10:20:22)
 * 
 * @author������
 */
public class ISNConvert
{

   private final static int nCharBase = 19975;

   private final static int nCharMax = 40863;

   private char cGBToBig5[] = new char[nCharMax - nCharBase + 1];

   private char cBig5ToGB[] = new char[nCharMax - nCharBase + 1];

   private static ISNConvert cjtf;

   /**
    * ISNConvert ������ע�⡣
    */
   public ISNConvert()
   {
      super();
      initialize();
   }

   public static String big5ToGb(String str)
   {

      return getInstance().big5ToGb_0(str);
   }

   private String big5ToGb_0(String str)
   {

      if (str == null || str.length() == 0)
         return str;
      char ca[] = str.toCharArray();
      boolean changed = false;
      for (int i = 0; i < ca.length; i++)
      {
         char c = ca[i];
         if (c < nCharBase || c > nCharMax)
            continue;
         char c1 = cBig5ToGB[c - nCharBase];
         if (c1 == 0)
            continue;
         ca[i] = c1;
         changed = true;
      }
      if (!changed)
         return str;

      return new String(ca);
   }

   public static String gbToBig5(String str)
   {

      return getInstance().gbToBig5_0(str);
   }

   private String gbToBig5_0(String str)
   {

      if (str == null || str.length() == 0)
         return str;
      char ca[] = str.toCharArray();
      boolean changed = false;
      for (int i = 0; i < ca.length; i++)
      {
         char c = ca[i];
         if (c < nCharBase || c > nCharMax)
            continue;
         char c1 = cGBToBig5[c - nCharBase];
         if (c1 == 0)
            continue;
         ca[i] = c1;
         changed = true;
      }
      if (!changed)
         return str;

      return new String(ca);
   }

   private static ISNConvert getInstance()
   {

      if (cjtf == null)
      {
         cjtf = new ISNConvert();
      }

      return cjtf;
   }

   /**
    * �˴����뷽��˵���� 
    * �������ڣ�(2002-3-8 9:53:36)
    */
   private void initialize()
   {

      //load();
      for (int i = 0; i < cBig5ToGB.length; i++)
      {
         cBig5ToGB[i] = 0;
         cGBToBig5[i] = 0;
      }
      load1();
      load2();

   }

   private void load1()
   {

      char gbToBig5_1[] =
      {(char) 19975, (char) 33836, (char) 19982, (char) 33287, (char) 19987, (char) 23560, (char) 19994, (char) 26989,
            (char) 19995, (char) 21474, (char) 19996, (char) 26481, (char) 19997, (char) 32114, (char) 20002,
            (char) 19999, (char) 20004, (char) 20841, (char) 20005, (char) 22196, (char) 20007, (char) 21930,
            (char) 20010, (char) 20491, (char) 20020, (char) 33256, (char) 20026, (char) 28858, (char) 20029,
            (char) 40599, (char) 20030, (char) 33289, (char) 20041, (char) 32681, (char) 20044, (char) 28879,
            (char) 20048, (char) 27138, (char) 20052, (char) 21932, (char) 20064, (char) 32722, (char) 20065,
            (char) 37129, (char) 20070, (char) 26360, (char) 20080, (char) 36023, (char) 20081, (char) 20098,
            (char) 20105, (char) 29229, (char) 20111, (char) 34407, (char) 20120, (char) 20121, (char) 20122,
            (char) 20126, (char) 20135, (char) 29986, (char) 20137, (char) 30045, (char) 20146, (char) 35242,
            (char) 20149, (char) 35131, (char) 20159, (char) 20740, (char) 20165, (char) 20677, (char) 20174,
            (char) 24478, (char) 20177, (char) 20374, (char) 20179, (char) 20489, (char) 20202, (char) 20736,
            (char) 20204, (char) 20497, (char) 20215, (char) 20729, (char) 20247, (char) 30526, (char) 20248,
            (char) 20778, (char) 20250, (char) 26371, (char) 20251, (char) 20660, (char) 20254, (char) 20632,
            (char) 20255, (char) 20553, (char) 20256, (char) 20659, (char) 20260, (char) 20663, (char) 20261,
            (char) 20480, (char) 20262, (char) 20523, (char) 20263, (char) 20630, (char) 20266, (char) 20605,
            (char) 20267, (char) 20295, (char) 20307, (char) 39636, (char) 20325, (char) 20681, (char) 20384,
            (char) 20448, (char) 20387, (char) 20406, (char) 20389, (char) 20709, (char) 20390, (char) 20597,
            (char) 20391, (char) 20596, (char) 20392, (char) 20689, (char) 20393, (char) 20744, (char) 20394,
            (char) 20757, (char) 20396, (char) 20738, (char) 20451, (char) 20417, (char) 20454, (char) 20756,
            (char) 20456, (char) 20796, (char) 20457, (char) 20486, (char) 20458, (char) 20791, (char) 20461,
            (char) 20745, (char) 20538, (char) 20661, (char) 20542, (char) 20670, (char) 20588, (char) 20655,
            (char) 20603, (char) 20674, (char) 20606, (char) 20712, (char) 20607, (char) 20767, (char) 20645,
            (char) 20795, (char) 20647, (char) 20752, (char) 20648, (char) 20786, (char) 20649, (char) 20794,
            (char) 20799, (char) 20818, (char) 20817, (char) 20812, (char) 20822, (char) 20823, (char) 20826,
            (char) 40680, (char) 20848, (char) 34349, (char) 20851, (char) 38364, (char) 20852, (char) 33288,
            (char) 20857, (char) 33586, (char) 20859, (char) 39178, (char) 20861, (char) 29560, (char) 20865,
            (char) 22213, (char) 20869, (char) 20839, (char) 20872, (char) 23713, (char) 20876, (char) 20874,
            (char) 20889, (char) 23531, (char) 20891, (char) 36557, (char) 20892, (char) 36786, (char) 20898,
            (char) 22618, (char) 20911, (char) 39342, (char) 20914, (char) 27798, (char) 20915, (char) 27770,
            (char) 20917, (char) 27841, (char) 20923, (char) 20941, (char) 20928, (char) 28136, (char) 20932,
            (char) 28114, (char) 20937, (char) 28092, (char) 20943, (char) 28187, (char) 20945, (char) 28234,
            (char) 20955, (char) 20956, (char) 20964, (char) 40179, (char) 20971, (char) 40167, (char) 20973,
            (char) 24977, (char) 20975, (char) 20977, (char) 20987, (char) 25802, (char) 20991, (char) 38015,
            (char) 21005, (char) 33467, (char) 21016, (char) 21129, (char) 21017, (char) 21063, (char) 21018,
            (char) 21083, (char) 21019, (char) 21109, (char) 21024, (char) 21034, (char) 21035, (char) 21029,
            (char) 21037, (char) 21060, (char) 21049, (char) 21070, (char) 21053, (char) 21130, (char) 21055,
            (char) 21132, (char) 21056, (char) 21108, (char) 21058, (char) 21137, (char) 21072, (char) 21102,
            (char) 21073, (char) 21133, (char) 21093, (char) 21085, (char) 21095, (char) 21127, (char) 21149,
            (char) 21240, (char) 21150, (char) 36774, (char) 21153, (char) 21209, (char) 21154, (char) 21233,
            (char) 21160, (char) 21205, (char) 21169, (char) 21237, (char) 21170, (char) 21185, (char) 21171,
            (char) 21214, (char) 21183, (char) 21218, (char) 21195, (char) 21211, (char) 21248, (char) 21243,
            (char) 21286, (char) 21293, (char) 21294, (char) 21297, (char) 21306, (char) 21312, (char) 21307,
            (char) 37291, (char) 21326, (char) 33775, (char) 21327, (char) 21332, (char) 21333, (char) 21934,
            (char) 21334, (char) 36067, (char) 21346, (char) 30439, (char) 21348, (char) 40565, (char) 21351,
            (char) 33253, (char) 21355, (char) 34907, (char) 21364, (char) 21371, (char) 21370, (char) 24057,
            (char) 21378, (char) 24288, (char) 21381, (char) 24307, (char) 21382, (char) 27511, (char) 21385,
            (char) 21426, (char) 21387, (char) 22739, (char) 21388, (char) 21421, (char) 21389, (char) 21401,
            (char) 21397, (char) 24257, (char) 21410, (char) 24258, (char) 21411, (char) 21428, (char) 21414,
            (char) 24264, (char) 21416, (char) 24282, (char) 21417, (char) 24260, (char) 21439, (char) 32291,
            (char) 21441, (char) 19977, (char) 21442, (char) 21443, (char) 21452, (char) 38617, (char) 21457,
            (char) 30332, (char) 21464, (char) 35722, (char) 21465, (char) 25944, (char) 21472, (char) 30090,
            (char) 21494, (char) 33865, (char) 21495, (char) 34399, (char) 21497, (char) 22022, (char) 21501,
            (char) 22064, (char) 21523, (char) 22151, (char) 21525, (char) 21570, (char) 21527, (char) 21966,
            (char) 21544, (char) 22136, (char) 21548, (char) 32893, (char) 21551, (char) 21855, (char) 21556,
            (char) 21555, (char) 21584, (char) 21558, (char) 21586, (char) 22072, (char) 21587, (char) 22216,
            (char) 21589, (char) 22036, (char) 21590, (char) 22182, (char) 21591, (char) 21764, (char) 21592,
            (char) 21729, (char) 21595, (char) 21958, (char) 21596, (char) 21978, (char) 21647, (char) 35424,
            (char) 21657, (char) 22184, (char) 21659, (char) 22144, (char) 21709, (char) 38911, (char) 21713,
            (char) 21854, (char) 21714, (char) 22112, (char) 21715, (char) 22069, (char) 21716, (char) 22006,
            (char) 21717, (char) 22118, (char) 21719, (char) 22057, (char) 21721, (char) 22130, (char) 21724,
            (char) 22156, (char) 21725, (char) 22117, (char) 21727, (char) 21938, (char) 21787, (char) 22044,
            (char) 21792, (char) 22062, (char) 21794, (char) 21993, (char) 21796, (char) 21914, (char) 21863,
            (char) 22038, (char) 21868, (char) 21959, (char) 21869, (char) 22208, (char) 21870, (char) 22169,
            (char) 21880, (char) 22063, (char) 21943, (char) 22132, (char) 21949, (char) 22029, (char) 21950,
            (char) 22195, (char) 21995, (char) 22209, (char) 22003, (char) 22127, (char) 22040, (char) 22099,
            (char) 22052, (char) 22198, (char) 22065, (char) 22225, (char) 22108, (char) 22165, (char) 22179,
            (char) 22210, (char) 22242, (char) 22296, (char) 22253, (char) 22290, (char) 22257, (char) 22250,
            (char) 22260, (char) 22285, (char) 22261, (char) 22279, (char) 22269, (char) 22283, (char) 22270,
            (char) 22294, (char) 22278, (char) 22291, (char) 22307, (char) 32854, (char) 22329, (char) 22745,
            (char) 22330, (char) 22580, (char) 22351, (char) 22750, (char) 22359, (char) 22602, (char) 22362,
            (char) 22533, (char) 22363, (char) 22727, (char) 22364, (char) 22754, (char) 22365, (char) 22761,
            (char) 22366, (char) 22626, (char) 22367, (char) 22707, (char) 22368, (char) 22684, (char) 22404,
            (char) 22751, (char) 22406, (char) 22746, (char) 22418, (char) 22744, (char) 22438, (char) 22718,
            (char) 22441, (char) 22538, (char) 22443, (char) 22666, (char) 22445, (char) 22497, (char) 22450,
            (char) 22607, (char) 22488, (char) 22610, (char) 22489, (char) 22628, (char) 22490, (char) 22557,
            (char) 22545, (char) 22649, (char) 22549, (char) 22702, (char) 22681, (char) 29254, (char) 22766,
            (char) 22767, (char) 22768, (char) 32882, (char) 22771, (char) 27580, (char) 22774, (char) 22778,
            (char) 22788, (char) 34389, (char) 22791, (char) 20633, (char) 22797, (char) 24489, (char) 22815,
            (char) 22816, (char) 22836, (char) 38957, (char) 22841, (char) 22846, (char) 22842, (char) 22890,
            (char) 22849, (char) 22889, (char) 22850, (char) 22864, (char) 22859, (char) 22894, (char) 22870,
            (char) 29518, (char) 22885, (char) 22887, (char) 22918, (char) 22941, (char) 22919, (char) 23142,
            (char) 22920, (char) 23229, (char) 22953, (char) 23285, (char) 22954, (char) 23255, (char) 22955,
            (char) 23215, (char) 22999, (char) 22989, (char) 23044, (char) 23105, (char) 23045, (char) 23149,
            (char) 23046, (char) 23304, (char) 23047, (char) 23308, (char) 23048, (char) 23372, (char) 23089,
            (char) 23067, (char) 23090, (char) 23207, (char) 23092, (char) 23291, (char) 23156, (char) 23344,
            (char) 23157, (char) 23307, (char) 23158, (char) 23352, (char) 23210, (char) 23228, (char) 23250,
            (char) 23329, (char) 23252, (char) 23338, (char) 23281, (char) 23321, (char) 23351, (char) 23332,
            (char) 23385, (char) 23403, (char) 23398, (char) 23416, (char) 23402, (char) 23423, (char) 23425,
            (char) 23527, (char) 23453, (char) 23542, (char) 23454, (char) 23526, (char) 23456, (char) 23541,
            (char) 23457, (char) 23529, (char) 23466, (char) 25010, (char) 23467, (char) 23470, (char) 23485,
            (char) 23532, (char) 23486, (char) 36051, (char) 23517, (char) 23522, (char) 23545, (char) 23565,
            (char) 23547, (char) 23563, (char) 23548, (char) 23566, (char) 23551, (char) 22781, (char) 23558,
            (char) 23559, (char) 23572, (char) 29246, (char) 23576, (char) 22645, (char) 23581, (char) 22039,
            (char) 23591, (char) 22575, (char) 23604, (char) 23607, (char) 23613, (char) 30433, (char) 23618,
            (char) 23652, (char) 23625, (char) 23644, (char) 23626, (char) 23622, (char) 23646, (char) 23660,
            (char) 23649, (char) 23650, (char) 23654, (char) 23656, (char) 23679, (char) 23996, (char) 23681,
            (char) 27506, (char) 23682, (char) 35912, (char) 23702, (char) 23943, (char) 23703, (char) 23831,
            (char) 23704, (char) 23796, (char) 23706, (char) 23888, (char) 23707, (char) 23798, (char) 23725,
            (char) 23994, (char) 23743, (char) 24011, (char) 23748, (char) 23975, (char) 23777, (char) 23805,
            (char) 23780, (char) 23968, (char) 23781, (char) 23842, (char) 23782, (char) 24018, (char) 23810,
            (char) 23959, (char) 23811, (char) 23821, (char) 23853, (char) 23940, (char) 23896, (char) 23992,
            (char) 23899, (char) 23859, (char) 23901, (char) 23937, (char) 24005, (char) 24020, (char) 24041,
            (char) 38799, (char) 24047, (char) 32967, (char) 24065, (char) 24163, (char) 24069, (char) 24101,
            (char) 24072, (char) 24107, (char) 24079, (char) 24131, (char) 24080, (char) 24115, (char) 24092,
            (char) 24159, (char) 24102, (char) 24118, (char) 24103, (char) 24128, (char) 24110, (char) 24171,
            (char) 24113, (char) 24172, (char) 24123, (char) 24152, (char) 24124, (char) 24151, (char) 24130,
            (char) 20906, (char) 24191, (char) 24291, (char) 24198, (char) 24950, (char) 24208, (char) 24300,
            (char) 24209, (char) 24289, (char) 24211, (char) 24235, (char) 24212, (char) 25033, (char) 24217,
            (char) 24287, (char) 24222, (char) 40848, (char) 24223, (char) 24290, (char) 24298, (char) 24297,
            (char) 24320, (char) 38283, (char) 24322, (char) 30064, (char) 24323, (char) 26820, (char) 24337,
            (char) 24338, (char) 24352, (char) 24373, (char) 24357, (char) 24396, (char) 24362, (char) 24371,
            (char) 24367, (char) 24398, (char) 24377, (char) 24392, (char) 24378, (char) 24375, (char) 24402,
            (char) 27512, (char) 24403, (char) 30070, (char) 24405, (char) 37636, (char) 24422, (char) 24421,
            (char) 24443, (char) 24505, (char) 24452, (char) 24465, (char) 24469, (char) 24480, (char) 24518,
            (char) 25014, (char) 24527, (char) 25082, (char) 24551, (char) 24962, (char) 24574, (char) 24894,
            (char) 24576, (char) 25079, (char) 24577, (char) 24907, (char) 24578, (char) 24939, (char) 24579,
            (char) 25006, (char) 24580, (char) 24938, (char) 24581, (char) 24757, (char) 24582, (char) 24884,
            (char) 24604, (char) 24976, (char) 24635, (char) 32317, (char) 24636, (char) 25055, (char) 24639,
            (char) 25036, (char) 24651, (char) 25088, (char) 24658, (char) 24646, (char) 24691, (char) 25031,
            (char) 24694, (char) 24801, (char) 24696, (char) 24927, (char) 24697, (char) 25064, (char) 24698,
            (char) 24887, (char) 24699, (char) 24827, (char) 24700, (char) 24817, (char) 24701, (char) 24818,
            (char) 24742, (char) 24709, (char) 24747, (char) 24872, (char) 24748, (char) 25080, (char) 24749,
            (char) 24947, (char) 24751, (char) 25003, (char) 24778, (char) 39514, (char) 24807, (char) 25084,
            (char) 24808, (char) 24920, (char) 24809, (char) 25074, (char) 24811, (char) 24970, (char) 24812,
            (char) 24860, (char) 24813, (char) 24922, (char) 24814, (char) 24986, (char) 24815, (char) 24931,
            (char) 24864, (char) 24909, (char) 24868, (char) 24996, (char) 24870, (char) 24978, (char) 24913,
            (char) 25086, (char) 25041, (char) 25059, (char) 25042, (char) 25078, (char) 25044, (char) 25037,
            (char) 25094, (char) 25095, (char) 25099, (char) 25108, (char) 25103, (char) 25138, (char) 25111,
            (char) 25127, (char) 25112, (char) 25136, (char) 25132, (char) 25129, (char) 25143, (char) 25142,
            (char) 25191, (char) 22519, (char) 25193, (char) 25844, (char) 25194, (char) 25451, (char) 25195,
            (char) 25475, (char) 25196, (char) 25562, (char) 25200, (char) 25854, (char) 25242, (char) 25771,
            (char) 25243, (char) 25291, (char) 25247, (char) 25718, (char) 25248, (char) 25715, (char) 25249,
            (char) 25476, (char) 25250, (char) 25654, (char) 25252, (char) 35703, (char) 25253, (char) 22577,
            (char) 25285, (char) 25812, (char) 25311, (char) 25836, (char) 25314, (char) 25871, (char) 25315,
            (char) 25536, (char) 25317, (char) 25793, (char) 25318, (char) 25876, (char) 25319, (char) 25840,
            (char) 25320, (char) 25765, (char) 25321, (char) 25799, (char) 25370, (char) 25711, (char) 25371,
            (char) 25891, (char) 25373, (char) 25790, (char) 25374, (char) 25787, (char) 25375, (char) 25406,
            (char) 25376, (char) 25747, (char) 25377, (char) 25803, (char) 25378, (char) 25759, (char) 25379,
            (char) 25497, (char) 25380, (char) 25824, (char) 25381, (char) 25582, (char) 25438, (char) 25736,
            (char) 25439, (char) 25613, (char) 25441, (char) 25791, (char) 25442, (char) 25563, (char) 25443,
            (char) 25623, (char) 25454, (char) 25818, (char) 25523, (char) 25796, (char) 25524, (char) 25681,
            (char) 25527, (char) 25842, (char) 25528, (char) 25763, (char) 25530, (char) 25723, (char) 25532,
            (char) 25692, (char) 25597, (char) 25900, (char) 25599, (char) 25779, (char) 25600, (char) 25881,
            (char) 25601, (char) 25841, (char) 25602, (char) 25695, (char) 25605, (char) 25898, (char) 25612,
            (char) 27024, (char) 25658, (char) 25884, (char) 25668, (char) 25885, (char) 25669, (char) 25860,
            (char) 25670, (char) 25850, (char) 25671, (char) 25622, (char) 25672, (char) 25839, (char) 25674,
            (char) 25892, (char) 25732, (char) 25878, (char) 25745, (char) 25744, (char) 25781, (char) 25862,
            (char) 25783, (char) 25847, (char) 25784, (char) 25852, (char) 25786, (char) 25883, (char) 25822,
            (char) 25851, (char) 25874, (char) 25890, (char) 25932, (char) 25973, (char) 25947, (char) 25986,
            (char) 25968, (char) 25976, (char) 25995, (char) 40779, (char) 26003, (char) 26005, (char) 26025,
            (char) 26028, (char) 26029, (char) 26039, (char) 26080, (char) 28961, (char) 26087, (char) 33290,
            (char) 26102, (char) 26178, (char) 26103, (char) 26336, (char) 26137, (char) 26311, (char) 26172,
            (char) 26205, (char) 26174, (char) 39023, (char) 26187, (char) 26185, (char) 26195, (char) 26313,
            (char) 26196, (char) 26308, (char) 26197, (char) 26248, (char) 26198, (char) 26249, (char) 26242,
            (char) 26283, (char) 26279, (char) 26326, (char) 26415, (char) 26414, (char) 26426, (char) 27231,
            (char) 26432, (char) 27578, (char) 26434, (char) 38620, (char) 26435, (char) 27402, (char) 26465,
            (char) 26781, (char) 26469, (char) 20358, (char) 26472, (char) 26954, (char) 26473, (char) 27050,
            (char) 26497, (char) 26997, (char) 26500, (char) 27083, (char) 26526, (char) 27141, (char) 26530,
            (char) 27166, (char) 26531, (char) 26839, (char) 26533, (char) 27370, (char) 26536, (char) 26838,
            (char) 26538, (char) 27085, (char) 26539, (char) 26963, (char) 26541, (char) 26783, (char) 26592,
            (char) 27320, (char) 26621, (char) 27273, (char) 26624, (char) 26772, (char) 26629, (char) 26613,
            (char) 26631, (char) 27161, (char) 26632, (char) 26855, (char) 26633, (char) 27355, (char) 26634,
            (char) 27379, (char) 26635, (char) 26847, (char) 26636, (char) 27368, (char) 26638, (char) 27359,
            (char) 26639, (char) 27396, (char) 26641, (char) 27193, (char) 26646, (char) 26866, (char) 26679,
            (char) 27171, (char) 26686, (char) 27410, (char) 26720, (char) 26895, (char) 26721, (char) 27208,
            (char) 26722, (char) 26984, (char) 26723, (char) 27284, (char) 26724, (char) 27071, (char) 26725,
            (char) 27211, (char) 26726, (char) 27194, (char) 26727, (char) 27292, (char) 26728, (char) 27123,
            (char) 26729, (char) 27137, (char) 26790, (char) 22818, (char) 26816, (char) 27298, (char) 26818,
            (char) 27386, (char) 26881, (char) 27112, (char) 26911, (char) 27357, (char) 26912, (char) 27111,
            (char) 26916, (char) 27407, (char) 26925, (char) 27234, (char) 27004, (char) 27155, (char) 27012,
            (char) 27414, (char) 27015, (char) 27372, (char) 27016, (char) 27354, (char) 27017, (char) 27384,
            (char) 27099, (char) 27323, (char) 27103, (char) 27315, (char) 27104, (char) 27367, (char) 27178,
            (char) 27243, (char) 27183, (char) 27299, (char) 27185, (char) 27387, (char) 27237, (char) 27371,
            (char) 27249, (char) 27365, (char) 27257, (char) 27347, (char) 27260, (char) 27358, (char) 27305,
            (char) 27265, (char) 27426, (char) 27489, (char) 27428, (char) 27487, (char) 27431, (char) 27472,
            (char) 27516, (char) 27570, (char) 27521, (char) 27519, (char) 27527, (char) 27556, (char) 27531,
            (char) 27544, (char) 27538, (char) 27550, (char) 27539, (char) 27566, (char) 27546, (char) 27563,
            (char) 27553, (char) 27567, (char) 27572, (char) 27590, (char) 27585, (char) 27584, (char) 27586,
            (char) 36674, (char) 27605, (char) 30050, (char) 27609, (char) 25987, (char) 27617, (char) 27656,
            (char) 27637, (char) 27647, (char) 27655, (char) 27660, (char) 27668, (char) 27683, (char) 27682,
            (char) 27691, (char) 27689, (char) 27692, (char) 27698, (char) 27699, (char) 27709, (char) 27718,
            (char) 27719, (char) 21295, (char) 27721, (char) 28450, (char) 27748, (char) 28271, (char) 27769,
            (char) 27958, (char) 27807, (char) 28317, (char) 27809, (char) 27794, (char) 27811, (char) 28739,
            (char) 27812, (char) 28442, (char) 27813, (char) 28701, (char) 27814, (char) 28138, (char) 27815,
            (char) 28356, (char) 27817, (char) 28296, (char) 27818, (char) 28396, (char) 27870, (char) 28632,
            (char) 27882, (char) 28122, (char) 27894, (char) 28585, (char) 27895, (char) 28711, (char) 27896,
            (char) 28696, (char) 27898, (char) 28668, (char) 27899, (char) 28681, (char) 27900, (char) 28497,
            (char) 27901, (char) 28580, (char) 27902, (char) 28039, (char) 27905, (char) 28500, (char) 27964,
            (char) 31402, (char) 27971, (char) 28025, (char) 27973, (char) 28154, (char) 27974, (char) 28479,
            (char) 27975, (char) 28550, (char) 27976, (char) 28254, (char) 27978, (char) 28609, (char) 27979,
            (char) 28204, (char) 27981, (char) 28590, (char) 27982, (char) 28639, (char) 27983, (char) 28687,
            (char) 27985, (char) 28222, (char) 27986, (char) 28408, (char) 27987, (char) 28611, (char) 27988,
            (char) 28527, (char) 28059, (char) 28644, (char) 28061, (char) 28551, (char) 28062, (char) 28150,
            (char) 28063, (char) 28451, (char) 28064, (char) 28543, (char) 28065, (char) 28198, (char) 28067,
            (char) 28185, (char) 28068, (char) 28364, (char) 28070, (char) 28516, (char) 28071, (char) 28567,
            (char) 28072, (char) 28466, (char) 28073, (char) 28544, (char) 28096, (char) 28593, (char) 28170,
            (char) 28149, (char) 28172, (char) 28133, (char) 28173, (char) 28460, (char) 28174, (char) 28678,
            (char) 28176, (char) 28472, (char) 28177, (char) 28576, (char) 28180, (char) 28417, (char) 28182,
            (char) 28683, (char) 28183, (char) 28402, (char) 28201, (char) 28331, (char) 28286, (char) 28771,
            (char) 28287, (char) 28629, (char) 28291, (char) 28528, (char) 28293, (char) 28666, (char) 28294,
            (char) 28469, (char) 28375, (char) 28535, (char) 28378, (char) 28414, (char) 28382, (char) 28399,
            (char) 28383, (char) 28777, (char) 28384, (char) 28740, (char) 28385, (char) 28415, (char) 28386,
            (char) 28677, (char) 28388, (char) 28670, (char) 28389, (char) 28651, (char) 28390, (char) 28772,
            (char) 28392, (char) 28657, (char) 28393, (char) 28760, (char) 28486, (char) 28704, (char) 28487,
            (char) 28703, (char) 28491, (char) 28722, (char) 28493, (char) 28656, (char) 28508, (char) 28507,
            (char) 28532, (char) 28710, (char) 28572, (char) 28734, (char) 28625, (char) 28712, (char) 28626,
            (char) 28693, (char) 28751, (char) 28765, (char) 28781, (char) 28357, (char) 28783, (char) 29128,
            (char) 28789, (char) 38728, (char) 28798, (char) 28797, (char) 28799, (char) 29158, (char) 28800,
            (char) 29036, (char) 28809, (char) 29200, (char) 28822, (char) 29129, (char) 28828, (char) 29010,
            (char) 28857, (char) 40670, (char) 28860, (char) 29001, (char) 28861, (char) 29118, (char) 28865,
            (char) 29197, (char) 28866, (char) 29211, (char) 28867, (char) 28916, (char) 28891, (char) 29165,
            (char) 28895, (char) 29017, (char) 28902, (char) 29033, (char) 28903, (char) 29138, (char) 28904,
            (char) 29121, (char) 28905, (char) 29172, (char) 28907, (char) 29145, (char) 28908, (char) 29180,
            (char) 28909, (char) 29105, (char) 28949, (char) 29029, (char) 28950, (char) 29148, (char) 28952,
            (char) 29182, (char) 29233, (char) 24859, (char) 29239, (char) 29242, (char) 29261, (char) 29272,
            (char) 29301, (char) 29309, (char) 29306, (char) 29351, (char) 29322, (char) 29346, (char) 29366,
            (char) 29376, (char) 29367, (char) 29559, (char) 29369, (char) 29494, (char) 29384, (char) 29437,
            (char) 29406, (char) 29552, (char) 29420, (char) 29544, (char) 29421, (char) 29433, (char) 29422,
            (char) 29509, (char) 29423, (char) 29546, (char) 29424, (char) 29465, (char) 29425, (char) 29508,
            (char) 29426, (char) 29499, (char) 29443, (char) 29547, (char) 29454, (char) 29557, (char) 29461,
            (char) 29564, (char) 29473, (char) 29568, (char) 29482, (char) 35948, (char) 29483, (char) 35987,
            (char) 29486, (char) 29563, (char) 29549, (char) 29562, (char) 29585, (char) 29859, (char) 29595,
            (char) 29802, (char) 29614, (char) 29771, (char) 29615, (char) 29872, (char) 29616, (char) 29694,
            (char) 29626, (char) 29885, (char) 29647, (char) 29608, (char) 29648, (char) 29754, (char) 29649,
            (char) 29903, (char) 29682, (char) 29759, (char) 29711, (char) 29833, (char) 29712, (char) 29795,
            (char) 29756, (char) 29898, (char) 29814, (char) 29796, (char) 29815, (char) 29862, (char) 29838,
            (char) 29908, (char) 29906, (char) 29914, (char) 29935, (char) 29964, (char) 30005, (char) 38651,
            (char) 30011, (char) 30059, (char) 30021, (char) 26274, (char) 30066, (char) 30060, (char) 30068,
            (char) 30087, (char) 30102, (char) 30308, (char) 30103, (char) 30274, (char) 30111, (char) 30247,
            (char) 30112, (char) 30296, (char) 30113, (char) 30221, (char) 30126, (char) 30241, (char) 30127,
            (char) 30219, (char) 30152, (char) 30320, (char) 30153, (char) 30169, (char) 30184, (char) 30278,
            (char) 30186, (char) 30227, (char) 30187, (char) 30279, (char) 30201, (char) 30202, (char) 30213,
            (char) 30281, (char) 30231, (char) 30238, (char) 30250, (char) 30303, (char) 30251, (char) 30321,
            (char) 30270, (char) 30318, (char) 30271, (char) 30317, (char) 30302, (char) 30313, (char) 30307,
            (char) 30316, (char) 30315, (char) 30322, (char) 30353, (char) 30362, (char) 30385, (char) 30394,
            (char) 30386, (char) 30392, (char) 30415, (char) 30430, (char) 30416, (char) 40573, (char) 30417,
            (char) 30435, (char) 30422, (char) 33995, (char) 30423, (char) 30428, (char) 30424, (char) 30436,
            (char) 30511, (char) 30599, (char) 30528, (char) 33879, (char) 30529, (char) 30556, (char) 30544,
            (char) 30558, (char) 30545, (char) 30652, (char) 30590, (char) 30570, (char) 30610, (char) 30622,
            (char) 30633, (char) 30682, (char) 30699, (char) 30703, (char) 30710, (char) 30959, (char) 30718,
            (char) 31020, (char) 30719, (char) 31014, (char) 30720, (char) 30893, (char) 30721, (char) 30908,
            (char) 30742, (char) 30938, (char) 30743, (char) 30824, (char) 30746, (char) 30831, (char) 30778,
            (char) 31018, (char) 30779, (char) 31025, (char) 30782, (char) 31019, (char) 30784, (char) 30990,
            (char) 30805, (char) 30889, (char) 30806, (char) 30820, (char) 30807, (char) 30973, (char) 30830,
            (char) 30906, (char) 30839, (char) 40572, (char) 30861, (char) 31001, (char) 30875, (char) 30951,
            (char) 30876, (char) 30947, (char) 30897, (char) 40572, (char) 31028, (char) 31009, (char) 31036,
            (char) 31150, (char) 31074, (char) 27319, (char) 31087, (char) 31118, (char) 31095, (char) 31153,
            (char) 31096, (char) 31117, (char) 31104, (char) 31263, (char) 31108, (char) 31103, (char) 31109,
            (char) 31146, (char) 31163, (char) 38626, (char) 31171, (char) 31167, (char) 31174, (char) 31240,
            (char) 31181, (char) 31278, (char) 31215, (char) 31309, (char) 31216, (char) 31281, (char) 31229,
            (char) 31330, (char) 31246, (char) 31237, (char) 31267, (char) 31308, (char) 31283, (char) 31337,
            (char) 31313, (char) 31329, (char) 31351, (char) 31406, (char) 31363, (char) 31434, (char) 31373,
            (char) 31429, (char) 31377, (char) 31407, (char) 31388, (char) 31428, (char) 31389, (char) 31401,
            (char) 31397, (char) 31418, (char) 31398, (char) 31431, (char) 31405, (char) 31414, (char) 31446,
            (char) 35918, (char) 31454, (char) 31478, (char) 31491, (char) 31716, (char) 31499, (char) 31565,
            (char) 31508, (char) 31558, (char) 31509, (char) 31591, (char) 31546, (char) 31627, (char) 31548,
            (char) 31840, (char) 31550, (char) 31849, (char) 31578, (char) 31731, (char) 31579, (char) 31721,
            (char) 31581, (char) 31631, (char) 31609, (char) 31820, (char) 31614, (char) 31805, (char) 31616,
            (char) 31777, (char) 31654, (char) 31744, (char) 31655, (char) 31691, (char) 31656, (char) 31836,
            (char) 31657, (char) 31854, (char) 31658, (char) 31774, (char) 31659, (char) 31787, (char) 31697,
            (char) 31779, (char) 31699, (char) 31757, (char) 31726, (char) 31811, (char) 31729, (char) 31852,
            (char) 31766, (char) 31850, (char) 31809, (char) 31839, (char) 31860, (char) 31988, (char) 31867,
            (char) 39006, (char) 31908, (char) 31925, (char) 31914, (char) 31966, (char) 31918, (char) 31975,
            (char) 31937, (char) 31965, (char) 32039, (char) 32202, (char) 32119, (char) 32310, (char) 32416,
            (char) 31998, (char) 32417, (char) 32006, (char) 32418, (char) 32005, (char) 32419, (char) 32002,
            (char) 32420, (char) 32406, (char) 32421, (char) 32007, (char) 32422, (char) 32004, (char) 32423,
            (char) 32026, (char) 32424, (char) 32008, (char) 32425, (char) 32394, (char) 32426, (char) 32000,
            (char) 32427, (char) 32009, (char) 32428, (char) 32239, (char) 32429, (char) 32028, (char) 32431,
            (char) 32020, (char) 32432, (char) 32021, (char) 32433, (char) 32023, (char) 32434, (char) 32177,
            (char) 32435, (char) 32013, (char) 32437, (char) 32305, (char) 32438, (char) 32184, (char) 32439,
            (char) 32027, (char) 32440, (char) 32025, (char) 32441, (char) 32011, (char) 32442, (char) 32033,
            (char) 32445, (char) 32016, (char) 32446, (char) 32019, (char) 32447, (char) 32218, (char) 32448,
            (char) 32058, (char) 32449, (char) 32050, (char) 32450, (char) 32049, (char) 32451, (char) 32244,
            (char) 32452, (char) 32068, (char) 32453, (char) 32051, (char) 32454, (char) 32048, (char) 32455,
            (char) 32340, (char) 32456, (char) 32066, (char) 32457, (char) 32272, (char) 32458, (char) 32070,
            (char) 32459, (char) 32060, (char) 32460, (char) 32064, (char) 32461, (char) 32057, (char) 32462,
            (char) 32377, (char) 32463, (char) 32147, (char) 32465, (char) 32129, (char) 32466, (char) 32104,
            (char) 32467, (char) 32080, (char) 32469, (char) 32350, (char) 32471, (char) 32078, (char) 32472,
            (char) 32362, (char) 32473, (char) 32102, (char) 32474, (char) 32098, (char) 32475, (char) 32115,
            (char) 32476, (char) 32097, (char) 32477, (char) 32085, (char) 32478, (char) 32094, (char) 32479,
            (char) 32113, (char) 32480, (char) 32134, (char) 32481, (char) 32131, (char) 32482, (char) 32121,
            (char) 32483, (char) 32353, (char) 32485, (char) 32143, (char) 32486, (char) 32091, (char) 32487,
            (char) 32380, (char) 32488, (char) 32136, (char) 32489, (char) 32318, (char) 32490, (char) 32210,
            (char) 32491, (char) 32190, (char) 32493, (char) 32396, (char) 32494, (char) 32186, (char) 32495,
            (char) 32203, (char) 32496, (char) 32189, (char) 32498, (char) 32196, (char) 32499, (char) 32361,
            (char) 32500, (char) 32173, (char) 32501, (char) 32191, (char) 32502, (char) 32172, (char) 32503,
            (char) 32323, (char) 32504, (char) 32162, (char) 32506, (char) 32185, (char) 32507, (char) 32163,
            (char) 32508, (char) 32156, (char) 32509, (char) 32187, (char) 32510, (char) 32176, (char) 32511,
            (char) 32160, (char) 32512, (char) 32180, (char) 32513, (char) 32199, (char) 32514, (char) 32217,
            (char) 32515, (char) 32215, (char) 32516, (char) 32216, (char) 32517, (char) 32236, (char) 32518,
            (char) 32412, (char) 32519, (char) 32249, (char) 32520, (char) 32242, (char) 32521, (char) 32221,
            (char) 32523, (char) 32354, (char) 32524, (char) 32230, (char) 32525, (char) 32158, (char) 32526,
            (char) 32222, (char) 32527, (char) 32246, (char) 32529, (char) 32241, (char) 32530, (char) 32267,
            (char) 32531, (char) 32233, (char) 32532, (char) 32224, (char) 32533, (char) 32311, (char) 32534,
            (char) 32232, (char) 32535, (char) 32225, (char) 32536, (char) 32227, (char) 32537, (char) 32265,
            (char) 32538, (char) 32283, (char) 32539, (char) 32287, (char) 32540, (char) 32285, (char) 32541,
            (char) 32299, (char) 32543, (char) 32286, (char) 32544, (char) 32399, (char) 32545, (char) 32301,
            (char) 32546, (char) 32266, (char) 32547, (char) 32273, (char) 32548, (char) 32381, (char) 32549,
            (char) 32313, (char) 32550, (char) 32309, (char) 32551, (char) 32306, (char) 32552, (char) 32403,
            (char) 32553, (char) 32302, (char) 32554, (char) 32326, (char) 32555, (char) 32325, (char) 32556,
            (char) 32392, (char) 32557, (char) 32346, (char) 32558, (char) 32341, (char) 32559, (char) 32338,
            (char) 32561, (char) 32382, (char) 32562, (char) 32368, (char) 32563, (char) 32367, (char) 32564,
            (char) 32371, (char) 32565, (char) 32408, (char) 32578, (char) 32588, (char) 32593, (char) 32178,
            (char) 32599, (char) 32645, (char) 32602, (char) 32624, (char) 32610, (char) 32631, (char) 32628,
            (char) 32646, (char) 32641, (char) 32648, (char) 32671, (char) 32677, (char) 32673, (char) 32680,
            (char) 32728, (char) 32761, (char) 32807, (char) 32812, (char) 32824, (char) 32883, (char) 32827,
            (char) 24677, (char) 32834, (char) 32886, (char) 32843, (char) 32894, (char) 32844, (char) 32887,
            (char) 32845, (char) 32889, (char) 32852, (char) 32879, (char) 32873, (char) 32885, (char) 32874,
            (char) 32880, (char) 32899, (char) 32901, (char) 32928, (char) 33144, (char) 32932, (char) 33178,
            (char) 32942, (char) 39599, (char) 32958, (char) 33102, (char) 32959, (char) 33131, (char) 32960,
            (char) 33081, (char) 32961, (char) 33029, (char) 32964, (char) 20881, (char) 32966, (char) 33213,
            (char) 32988, (char) 21213, (char) 32999, (char) 26407, (char) 33002, (char) 33242, (char) 33003,
            (char) 33051, (char) 33014, (char) 33184, (char) 33033, (char) 33032, (char) 33037, (char) 33214,
            (char) 33039, (char) 33247, (char) 33040, (char) 33229, (char) 33041, (char) 33126, (char) 33043,
            (char) 33215, (char) 33044, (char) 33248, (char) 33050, (char) 33139, (char) 33073, (char) 33067,
            (char) 33078, (char) 33121, (char) 33080, (char) 33225, (char) 33098, (char) 33240, (char) 33147,
            (char) 33193, (char) 33149, (char) 33155, (char) 33150, (char) 39472, (char) 33169, (char) 33231,
            (char) 33286, (char) 36671, (char) 33315, (char) 33380, (char) 33328, (char) 33382, (char) 33329,
            (char) 33369, (char) 33339, (char) 33387, (char) 33392, (char) 33393, (char) 33395, (char) 33399,
            (char) 33402, (char) 34269, (char) 33410, (char) 31680, (char) 33416, (char) 32651, (char) 33431,
            (char) 34188, (char) 33436, (char) 34154, (char) 33446, (char) 34310, (char) 33473, (char) 34031,
            (char) 33479, (char) 33894, (char) 33480, (char) 34294, (char) 33483, (char) 33703, (char) 33484,
            (char) 33799, (char) 33485, (char) 33980, (char) 33486, (char) 33511, (char) 33487, (char) 34311,
            (char) 33529, (char) 34315, (char) 33550, (char) 33686, (char) 33551, (char) 34338, (char) 33553,
            (char) 34086, (char) 33556, (char) 22603, (char) 33557, (char) 29026, (char) 33575, (char) 32365,
            (char) 33606, (char) 33610, (char) 33626, (char) 33698, (char) 33627, (char) 34136, (char) 33628,
            (char) 34045, (char) 33630, (char) 34126, (char) 33631, (char) 34184, (char) 33632, (char) 34234,
            (char) 33633, (char) 34153, (char) 33635, (char) 27054, (char) 33636, (char) 33911, (char) 33637,
            (char) 28366, (char) 33638, (char) 29334, (char) 33639, (char) 29074, (char) 33641, (char) 34254,
            (char) 33642, (char) 33984, (char) 33643, (char) 34093, (char) 33645, (char) 33874, (char) 33647,
            (char) 34277, (char) 33669, (char) 33950, (char) 33713, (char) 33802, (char) 33714, (char) 34030,
            (char) 33715, (char) 33940, (char) 33716, (char) 33845, (char) 33719, (char) 29554, (char) 33720,
            (char) 34133, (char) 33721, (char) 29801, (char) 33722, (char) 40367, (char) 33821, (char) 34367,
            (char) 33828, (char) 34722, (char) 33829, (char) 29151, (char) 33830, (char) 32264, (char) 33831,
            (char) 34157, (char) 33832, (char) 34217, (char) 33905, (char) 34085, (char) 33927, (char) 34118,
            (char) 33929, (char) 34146, (char) 33931, (char) 34083, (char) 33932, (char) 34078, (char) 34013,
            (char) 34253, (char) 34015, (char) 34186, (char) 34016, (char) 34362, (char) 34019, (char) 34167,
            (char) 34022, (char) 39488, (char) 34103, (char) 34196, (char) 34105, (char) 34334, (char) 34106,
            (char) 34298, (char) 34108, (char) 34297, (char) 34162, (char) 34308, (char) 34164, (char) 34314,
            (char) 34222, (char) 34282, (char) 34259, (char) 34330, (char) 34383, (char) 34396, (char) 34385,
            (char) 24942, (char) 34394, (char) 34395, (char) 34412, (char) 34415, (char) 34414, (char) 34787,
            (char) 34429, (char) 38614, (char) 34430, (char) 34662, (char) 34431, (char) 34822, (char) 34432,
            (char) 34645, (char) 34433, (char) 34811, (char) 34434, (char) 34718,
      /** ==========1199=== */
      };
      loadChar(gbToBig5_1);

   }

   private void load2()
   {

      char gbToBig5_2[] =
      {
      /** ==========1199=== */
      (char) 34453, (char) 34870, (char) 34476, (char) 34566, (char) 34506, (char) 34865, (char) 34510, (char) 34851,
            (char) 34511, (char) 34806, (char) 34542, (char) 34875, (char) 34544, (char) 34756, (char) 34545,
            (char) 34554, (char) 34546, (char) 34799, (char) 34547, (char) 34692, (char) 34548, (char) 34832,
            (char) 34581, (char) 34555, (char) 34583, (char) 34680, (char) 34593, (char) 34847, (char) 34631,
            (char) 34821, (char) 34632, (char) 34760, (char) 34633, (char) 34796, (char) 34638, (char) 34829,
            (char) 34684, (char) 34747, (char) 34686, (char) 34833, (char) 34876, (char) 34871, (char) 34885,
            (char) 33291, (char) 34900, (char) 37532, (char) 34917, (char) 35036, (char) 34924, (char) 35183,
            (char) 34926, (char) 34974, (char) 34948, (char) 35158, (char) 34949, (char) 35018, (char) 34972,
            (char) 35178, (char) 34989, (char) 35186, (char) 35013, (char) 35037, (char) 35014, (char) 35168,
            (char) 35042, (char) 35123, (char) 35043, (char) 35165, (char) 35044, (char) 35122, (char) 35045,
            (char) 35145, (char) 35099, (char) 35128, (char) 35124, (char) 35172, (char) 35265, (char) 35211,
            (char) 35266, (char) 35264, (char) 35268, (char) 35215, (char) 35269, (char) 35219, (char) 35270,
            (char) 35222, (char) 35271, (char) 35224, (char) 35272, (char) 35261, (char) 35273, (char) 35258,
            (char) 35274, (char) 35244, (char) 35275, (char) 35233, (char) 35276, (char) 35263, (char) 35278,
            (char) 35238, (char) 35279, (char) 35247, (char) 35280, (char) 35250, (char) 35281, (char) 35255,
            (char) 35294, (char) 35316, (char) 35302, (char) 35320, (char) 35311, (char) 35318, (char) 35465,
            (char) 35709, (char) 35466, (char) 35588, (char) 35745, (char) 35336, (char) 35746, (char) 35330,
            (char) 35747, (char) 35331, (char) 35748, (char) 35469, (char) 35749, (char) 35663, (char) 35750,
            (char) 35344, (char) 35751, (char) 35340, (char) 35752, (char) 35342, (char) 35753, (char) 35731,
            (char) 35754, (char) 35349, (char) 35755, (char) 35350, (char) 35757, (char) 35347, (char) 35758,
            (char) 35696, (char) 35759, (char) 35338, (char) 35760, (char) 35352, (char) 35762, (char) 35611,
            (char) 35763, (char) 35569, (char) 35764, (char) 35635, (char) 35765, (char) 35406, (char) 35766,
            (char) 35357, (char) 35767, (char) 35365, (char) 35768, (char) 35377, (char) 35769, (char) 35355,
            (char) 35770, (char) 35542, (char) 35772, (char) 35359, (char) 35773, (char) 35575, (char) 35774,
            (char) 35373, (char) 35775, (char) 35370, (char) 35776, (char) 35363, (char) 35777, (char) 35388,
            (char) 35778, (char) 35393, (char) 35779, (char) 35382, (char) 35780, (char) 35413, (char) 35781,
            (char) 35419, (char) 35782, (char) 35672, (char) 35784, (char) 35408, (char) 35785, (char) 35380,
            (char) 35786, (char) 35386, (char) 35787, (char) 35398, (char) 35788, (char) 35589, (char) 35789,
            (char) 35422, (char) 35791, (char) 35412, (char) 35793, (char) 35695, (char) 35794, (char) 35410,
            (char) 35795, (char) 35462, (char) 35796, (char) 35460, (char) 35797, (char) 35430, (char) 35798,
            (char) 35455, (char) 35799, (char) 35433, (char) 35800, (char) 35440, (char) 35801, (char) 35452,
            (char) 35802, (char) 35488, (char) 35803, (char) 35461, (char) 35804, (char) 35445, (char) 35805,
            (char) 35441, (char) 35806, (char) 35477, (char) 35807, (char) 35436, (char) 35808, (char) 35438,
            (char) 35809, (char) 35437, (char) 35810, (char) 35426, (char) 35811, (char) 35427, (char) 35812,
            (char) 35533, (char) 35813, (char) 35442, (char) 35814, (char) 35443, (char) 35815, (char) 35435,
            (char) 35816, (char) 35554, (char) 35817, (char) 35425, (char) 35819, (char) 35489, (char) 35820,
            (char) 35491, (char) 35821, (char) 35486, (char) 35822, (char) 35482, (char) 35823, (char) 35492,
            (char) 35824, (char) 35493, (char) 35825, (char) 35480, (char) 35826, (char) 35496, (char) 35827,
            (char) 35473, (char) 35828, (char) 35498, (char) 35829, (char) 35494, (char) 35830, (char) 35474,
            (char) 35831, (char) 35531, (char) 35832, (char) 35576, (char) 35833, (char) 35535, (char) 35834,
            (char) 35582, (char) 35835, (char) 35712, (char) 35836, (char) 35537, (char) 35837, (char) 35513,
            (char) 35838, (char) 35506, (char) 35839, (char) 35529, (char) 35840, (char) 35547, (char) 35841,
            (char) 35504, (char) 35842, (char) 35543, (char) 35843, (char) 35519, (char) 35844, (char) 35522,
            (char) 35845, (char) 35538, (char) 35846, (char) 35524, (char) 35847, (char) 35510, (char) 35848,
            (char) 35527, (char) 35850, (char) 35516, (char) 35851, (char) 35584, (char) 35852, (char) 35574,
            (char) 35853, (char) 35548, (char) 35854, (char) 35594, (char) 35855, (char) 35563, (char) 35856,
            (char) 35559, (char) 35857, (char) 35604, (char) 35858, (char) 35585, (char) 35859, (char) 35586,
            (char) 35860, (char) 35556, (char) 35861, (char) 35565, (char) 35862, (char) 35580, (char) 35863,
            (char) 35730, (char) 35864, (char) 35566, (char) 35865, (char) 35571, (char) 35866, (char) 35578,
            (char) 35867, (char) 35558, (char) 35868, (char) 35598, (char) 35869, (char) 35550, (char) 35871,
            (char) 35624, (char) 35872, (char) 35740, (char) 35873, (char) 35606, (char) 35874, (char) 35613,
            (char) 35875, (char) 35616, (char) 35876, (char) 35607, (char) 35877, (char) 35610, (char) 35878,
            (char) 35609, (char) 35879, (char) 35600, (char) 35880, (char) 35641, (char) 35881, (char) 35646,
            (char) 35882, (char) 35627, (char) 35883, (char) 35710, (char) 35884, (char) 35628, (char) 35885,
            (char) 35674, (char) 35886, (char) 35670, (char) 35887, (char) 35673, (char) 35888, (char) 35733,
            (char) 35889, (char) 35676, (char) 35890, (char) 35662, (char) 35891, (char) 35742, (char) 35892,
            (char) 35700, (char) 35893, (char) 35691, (char) 35894, (char) 35734, (char) 36125, (char) 35997,
            (char) 36126, (char) 35998, (char) 36127, (char) 36000, (char) 36129, (char) 36002, (char) 36130,
            (char) 36001, (char) 36131, (char) 36012, (char) 36132, (char) 36066, (char) 36133, (char) 25943,
            (char) 36134, (char) 36076, (char) 36135, (char) 36008, (char) 36136, (char) 36074, (char) 36137,
            (char) 36009, (char) 36138, (char) 36010, (char) 36139, (char) 36007, (char) 36140, (char) 36022,
            (char) 36141, (char) 36092, (char) 36142, (char) 36015, (char) 36143, (char) 36011, (char) 36144,
            (char) 36019, (char) 36145, (char) 36068, (char) 36146, (char) 36033, (char) 36147, (char) 36016,
            (char) 36148, (char) 36028, (char) 36149, (char) 36020, (char) 36150, (char) 36026, (char) 36151,
            (char) 36024, (char) 36152, (char) 36031, (char) 36153, (char) 36027, (char) 36154, (char) 36032,
            (char) 36155, (char) 36029, (char) 36156, (char) 36042, (char) 36157, (char) 36100, (char) 36158,
            (char) 36040, (char) 36159, (char) 36036, (char) 36160, (char) 36018, (char) 36161, (char) 36035,
            (char) 36162, (char) 36034, (char) 36163, (char) 36115, (char) 36164, (char) 36039, (char) 36165,
            (char) 36037, (char) 36166, (char) 36112, (char) 36167, (char) 36053, (char) 36168, (char) 36049,
            (char) 36169, (char) 36058, (char) 36170, (char) 36050, (char) 36171, (char) 36070, (char) 36172,
            (char) 36077, (char) 36174, (char) 36118, (char) 36175, (char) 36062, (char) 36176, (char) 36060,
            (char) 36179, (char) 36065, (char) 36180, (char) 36064, (char) 36181, (char) 36071, (char) 36182,
            (char) 36084, (char) 36184, (char) 36101, (char) 36185, (char) 36091, (char) 36186, (char) 36090,
            (char) 36187, (char) 36093, (char) 36188, (char) 36094, (char) 36189, (char) 36119, (char) 36190,
            (char) 36106, (char) 36192, (char) 36104, (char) 36193, (char) 36109, (char) 36194, (char) 36111,
            (char) 36195, (char) 36123, (char) 36213, (char) 36249, (char) 36214, (char) 36245, (char) 36235,
            (char) 36264, (char) 36273, (char) 36274, (char) 36280, (char) 36489, (char) 36291, (char) 36493,
            (char) 36292, (char) 36428, (char) 36318, (char) 36498, (char) 36341, (char) 36368, (char) 36343,
            (char) 36474, (char) 36344, (char) 36437, (char) 36345, (char) 36506, (char) 36347, (char) 36491,
            (char) 36362, (char) 36404, (char) 36364, (char) 36490, (char) 36394, (char) 36452, (char) 36396,
            (char) 36499, (char) 36399, (char) 36497, (char) 36433, (char) 36513, (char) 36434, (char) 36451,
            (char) 36464, (char) 36501, (char) 36479, (char) 36517, (char) 36495, (char) 36522, (char) 36508,
            (char) 36518, (char) 36527, (char) 36544, (char) 36710, (char) 36554, (char) 36711, (char) 36555,
            (char) 36712, (char) 36556, (char) 36713, (char) 36562, (char) 36715, (char) 36564, (char) 36716,
            (char) 36681, (char) 36717, (char) 36571, (char) 36718, (char) 36650, (char) 36719, (char) 36575,
            (char) 36720, (char) 36703, (char) 36722, (char) 36603, (char) 36723, (char) 36708, (char) 36724,
            (char) 36600, (char) 36725, (char) 36601, (char) 36726, (char) 36604, (char) 36728, (char) 36587,
            (char) 36729, (char) 36706, (char) 36730, (char) 36602, (char) 36731, (char) 36629, (char) 36732,
            (char) 36606, (char) 36733, (char) 36617, (char) 36734, (char) 36618, (char) 36735, (char) 36686,
            (char) 36737, (char) 36615, (char) 36738, (char) 36613, (char) 36739, (char) 36611, (char) 36740,
            (char) 36626, (char) 36741, (char) 36628, (char) 36742, (char) 36635, (char) 36743, (char) 36646,
            (char) 36744, (char) 36649, (char) 36745, (char) 36637, (char) 36746, (char) 36645, (char) 36747,
            (char) 36638, (char) 36749, (char) 36639, (char) 36750, (char) 36636, (char) 36751, (char) 36659,
            (char) 36752, (char) 36667, (char) 36753, (char) 36655, (char) 36755, (char) 36664, (char) 36756,
            (char) 36705, (char) 36757, (char) 36677, (char) 36758, (char) 36676, (char) 36759, (char) 36670,
            (char) 36760, (char) 36678, (char) 36761, (char) 36685, (char) 36762, (char) 36692, (char) 36766,
            (char) 36781, (char) 36777, (char) 36783, (char) 36779, (char) 36782, (char) 36793, (char) 37002,
            (char) 36797, (char) 36988, (char) 36798, (char) 36948, (char) 36801, (char) 36983, (char) 36807,
            (char) 36942, (char) 36808, (char) 36993, (char) 36816, (char) 36939, (char) 36824, (char) 36996,
            (char) 36825, (char) 36889, (char) 36827, (char) 36914, (char) 36828, (char) 36960, (char) 36829,
            (char) 36949, (char) 36830, (char) 36899, (char) 36831, (char) 36978, (char) 36841, (char) 36999,
            (char) 36851, (char) 36885, (char) 36857, (char) 36321, (char) 36866, (char) 36969, (char) 36873,
            (char) 36984, (char) 36874, (char) 36956, (char) 36882, (char) 36958, (char) 36902, (char) 37008,
            (char) 36923, (char) 37007, (char) 36951, (char) 36986, (char) 36965, (char) 36953, (char) 37011,
            (char) 37159, (char) 37021, (char) 37178, (char) 37036, (char) 37140, (char) 37038, (char) 37109,
            (char) 37049, (char) 37138, (char) 37050, (char) 37172, (char) 37051, (char) 37168, (char) 37071,
            (char) 37087, (char) 37072, (char) 37174, (char) 37073, (char) 37165, (char) 37075, (char) 37126,
            (char) 37094, (char) 37192, (char) 37095, (char) 37142, (char) 37112, (char) 37170, (char) 37213,
            (char) 37278, (char) 37233, (char) 37292, (char) 37245, (char) 37317, (char) 37246, (char) 37315,
            (char) 37247, (char) 37312, (char) 37322, (char) 37323, (char) 37492, (char) 37970, (char) 37550,
            (char) 38014, (char) 37694, (char) 37864, (char) 38022, (char) 37331, (char) 38023, (char) 37332,
            (char) 38024, (char) 37341, (char) 38025, (char) 37336, (char) 38026, (char) 37335, (char) 38027,
            (char) 37337, (char) 38028, (char) 37333, (char) 38029, (char) 37367, (char) 38031, (char) 37351,
            (char) 38032, (char) 37348, (char) 38034, (char) 37353, (char) 38035, (char) 37347, (char) 38036,
            (char) 37702, (char) 38037, (char) 37369, (char) 38039, (char) 37365, (char) 38041, (char) 37411,
            (char) 38043, (char) 37414, (char) 38044, (char) 37445, (char) 38045, (char) 37389, (char) 38046,
            (char) 37396, (char) 38047, (char) 37912, (char) 38048, (char) 37385, (char) 38049, (char) 37575,
            (char) 38050, (char) 37628, (char) 38051, (char) 37393, (char) 38052, (char) 37392, (char) 38053,
            (char) 38000, (char) 38054, (char) 27453, (char) 38055, (char) 37406, (char) 38056, (char) 37794,
            (char) 38057, (char) 37476, (char) 38058, (char) 37415, (char) 38059, (char) 37377, (char) 38060,
            (char) 37413, (char) 38061, (char) 37380, (char) 38062, (char) 37397, (char) 38063, (char) 37376,
            (char) 38064, (char) 37434, (char) 38065, (char) 37666, (char) 38066, (char) 37478, (char) 38067,
            (char) 37463, (char) 38068, (char) 37431, (char) 38069, (char) 32573, (char) 38070, (char) 37427,
            (char) 38072, (char) 37437, (char) 38073, (char) 37432, (char) 38074, (char) 37470, (char) 38075,
            (char) 38013, (char) 38076, (char) 37484, (char) 38077, (char) 37485, (char) 38078, (char) 37440,
            (char) 38079, (char) 37439, (char) 38080, (char) 37438, (char) 38081, (char) 37941, (char) 38082,
            (char) 37457, (char) 38083, (char) 37428, (char) 38084, (char) 37984, (char) 38085, (char) 37467,
            (char) 38086, (char) 37466, (char) 38088, (char) 37424, (char) 38089, (char) 37449, (char) 38090,
            (char) 37448, (char) 38091, (char) 37453, (char) 38092, (char) 37422, (char) 38093, (char) 37433,
            (char) 38094, (char) 37944, (char) 38096, (char) 37548, (char) 38097, (char) 37536, (char) 38098,
            (char) 37498, (char) 38101, (char) 37546, (char) 38102, (char) 37614, (char) 38103, (char) 37583,
            (char) 38105, (char) 37891, (char) 38107, (char) 37946, (char) 38108, (char) 37509, (char) 38109,
            (char) 37569, (char) 38111, (char) 37542, (char) 38112, (char) 37799, (char) 38113, (char) 37720,
            (char) 38114, (char) 37526, (char) 38115, (char) 37521, (char) 38116, (char) 37580, (char) 38117,
            (char) 37545, (char) 38119, (char) 37877, (char) 38120, (char) 37523, (char) 38121, (char) 37801,
            (char) 38122, (char) 37503, (char) 38123, (char) 37530, (char) 38124, (char) 37499, (char) 38125,
            (char) 37528, (char) 38126, (char) 37658, (char) 38127, (char) 37547, (char) 38128, (char) 37496,
            (char) 38129, (char) 37541, (char) 38130, (char) 37855, (char) 38131, (char) 37507, (char) 38132,
            (char) 37899, (char) 38133, (char) 37544, (char) 38134, (char) 37504, (char) 38135, (char) 37539,
            (char) 38136, (char) 37956, (char) 38137, (char) 37906, (char) 38138, (char) 37610, (char) 38140,
            (char) 37688, (char) 38141, (char) 37617, (char) 38142, (char) 37832, (char) 38143, (char) 37847,
            (char) 38144, (char) 37559, (char) 38145, (char) 37782, (char) 38146, (char) 37616, (char) 38148,
            (char) 37604, (char) 38149, (char) 37707, (char) 38150, (char) 37615, (char) 38151, (char) 37608,
            (char) 38152, (char) 37885, (char) 38153, (char) 37564, (char) 38154, (char) 37597, (char) 38155,
            (char) 37586, (char) 38156, (char) 37573, (char) 38160, (char) 37555, (char) 38161, (char) 37563,
            (char) 38162, (char) 37571, (char) 38163, (char) 37599, (char) 38164, (char) 37606, (char) 38165,
            (char) 37650, (char) 38166, (char) 37638, (char) 38167, (char) 37754, (char) 38169, (char) 37679,
            (char) 38170, (char) 37672, (char) 38171, (char) 37659, (char) 38174, (char) 37633, (char) 38175,
            (char) 37653, (char) 38177, (char) 37675, (char) 38178, (char) 37678, (char) 38179, (char) 38012,
            (char) 38180, (char) 37656, (char) 38181, (char) 37648, (char) 38182, (char) 37670, (char) 38185,
            (char) 37640, (char) 38188, (char) 37663, (char) 38189, (char) 37664, (char) 38190, (char) 37749,
            (char) 38191, (char) 37624, (char) 38192, (char) 37683, (char) 38193, (char) 37657, (char) 38194,
            (char) 37733, (char) 38196, (char) 37703, (char) 38197, (char) 37848, (char) 38198, (char) 37750,
            (char) 38199, (char) 37716, (char) 38200, (char) 37732, (char) 38201, (char) 37740, (char) 38202,
            (char) 37758, (char) 38203, (char) 37723, (char) 38204, (char) 37802, (char) 38206, (char) 37744,
            (char) 38208, (char) 37709, (char) 38209, (char) 37762, (char) 38210, (char) 37860, (char) 38214,
            (char) 37836, (char) 38215, (char) 37806, (char) 38217, (char) 37784, (char) 38218, (char) 38007,
            (char) 38220, (char) 37931, (char) 38221, (char) 37811, (char) 38223, (char) 37798, (char) 38224,
            (char) 37804, (char) 38225, (char) 37770, (char) 38226, (char) 37808, (char) 38227, (char) 37813,
            (char) 38228, (char) 37964, (char) 38230, (char) 37858, (char) 38231, (char) 37852, (char) 38232,
            (char) 37853, (char) 38233, (char) 37837, (char) 38235, (char) 37854, (char) 38236, (char) 37857,
            (char) 38237, (char) 37841, (char) 38238, (char) 37827, (char) 38239, (char) 37831, (char) 38241,
            (char) 37908, (char) 38243, (char) 37904, (char) 38244, (char) 37879, (char) 38246, (char) 37907,
            (char) 38247, (char) 37997, (char) 38248, (char) 37920, (char) 38250, (char) 37881, (char) 38251,
            (char) 37913, (char) 38252, (char) 37962, (char) 38253, (char) 37939, (char) 38255, (char) 37938,
            (char) 38256, (char) 37934, (char) 38257, (char) 37951, (char) 38259, (char) 37987, (char) 38262,
            (char) 38002, (char) 38271, (char) 38263, (char) 38376, (char) 38272, (char) 38377, (char) 38274,
            (char) 38378, (char) 38275, (char) 38379, (char) 38278, (char) 38381, (char) 38281, (char) 38382,
            (char) 21839, (char) 38383, (char) 38358, (char) 38384, (char) 38287, (char) 38385, (char) 38344,
            (char) 38386, (char) 38289, (char) 38387, (char) 38286, (char) 38388, (char) 38291, (char) 38389,
            (char) 38292, (char) 38390, (char) 38284, (char) 38391, (char) 24758, (char) 38392, (char) 38296,
            (char) 38393, (char) 39719, (char) 38394, (char) 38312, (char) 38395, (char) 32862, (char) 38396,
            (char) 38373, (char) 38397, (char) 38313, (char) 38398, (char) 38317, (char) 38400, (char) 38309,
            (char) 38401, (char) 38307, (char) 38402, (char) 38305, (char) 38403, (char) 38315, (char) 38404,
            (char) 39726, (char) 38405, (char) 38321, (char) 38406, (char) 38316, (char) 38408, (char) 38334,
            (char) 38409, (char) 38329, (char) 38410, (char) 38326, (char) 38412, (char) 38335, (char) 38413,
            (char) 38333, (char) 38414, (char) 38331, (char) 38415, (char) 38332, (char) 38416, (char) 38369,
            (char) 38417, (char) 38348, (char) 38418, (char) 38339, (char) 38420, (char) 38346, (char) 38421,
            (char) 38347, (char) 38422, (char) 38356, (char) 38423, (char) 38352, (char) 38425, (char) 38357,
            (char) 38426, (char) 38366, (char) 38431, (char) 38538, (char) 38451, (char) 38525, (char) 38452,
            (char) 38512, (char) 38453, (char) 38499, (char) 38454, (char) 38542, (char) 38469, (char) 38555,
            (char) 38470, (char) 38520, (char) 38471, (char) 38580, (char) 38472, (char) 38515, (char) 38473,
            (char) 38488, (char) 38485, (char) 38493, (char) 38503, (char) 38537, (char) 38504, (char) 38549,
            (char) 38505, (char) 38570, (char) 38543, (char) 38568, (char) 38544, (char) 38577, (char) 38582,
            (char) 38584, (char) 38589, (char) 38603, (char) 38590, (char) 38627, (char) 38607, (char) 38619,
            (char) 38624, (char) 35726, (char) 38643, (char) 38722, (char) 38654, (char) 38695, (char) 38657,
            (char) 38717, (char) 38701, (char) 38724, (char) 38739, (char) 38746, (char) 38745, (char) 38748,
            (char) 38757, (char) 38760, (char) 38801, (char) 38851, (char) 38831, (char) 38857, (char) 38886,
            (char) 38859, (char) 38887, (char) 38860, (char) 38889, (char) 38867, (char) 38890, (char) 38873,
            (char) 38891, (char) 38878, (char) 38892, (char) 38876, (char) 38901, (char) 38907, (char) 39029,
            (char) 38913, (char) 39030, (char) 38914, (char) 39031, (char) 38915, (char) 39032, (char) 38919,
            (char) 39033, (char) 38917, (char) 39034, (char) 38918, (char) 39035, (char) 38920, (char) 39036,
            (char) 38922, (char) 39037, (char) 38929, (char) 39038, (char) 39015, (char) 39039, (char) 38931,
            (char) 39040, (char) 38926, (char) 39041, (char) 38930, (char) 39042, (char) 38924, (char) 39043,
            (char) 38927, (char) 39044, (char) 38928, (char) 39045, (char) 39025, (char) 39046, (char) 38936,
            (char) 39047, (char) 38935, (char) 39048, (char) 38968, (char) 39049, (char) 38945, (char) 39050,
            (char) 38960, (char) 39052, (char) 38940, (char) 39055, (char) 38950, (char) 39056, (char) 38948,
            (char) 39057, (char) 38971, (char) 39059, (char) 38969, (char) 39060, (char) 38967, (char) 39062,
            (char) 31310, (char) 39063, (char) 38982, (char) 39064, (char) 38988, (char) 39066, (char) 38990,
            (char) 39067, (char) 38995, (char) 39068, (char) 38991, (char) 39069, (char) 38989, (char) 39070,
            (char) 39027, (char) 39071, (char) 39010, (char) 39072, (char) 39003, (char) 39073, (char) 39001,
            (char) 39074, (char) 39013, (char) 39076, (char) 39019, (char) 39078, (char) 39024, (char) 39079,
            (char) 39028, (char) 39118, (char) 39080, (char) 39121, (char) 39086, (char) 39122, (char) 39087,
            (char) 39123, (char) 39094, (char) 39125, (char) 39100, (char) 39128, (char) 39108, (char) 39129,
            (char) 39110, (char) 39134, (char) 39131, (char) 39144, (char) 39255, (char) 39181, (char) 39260,
            (char) 39269, (char) 39138, (char) 39271, (char) 39219, (char) 39272, (char) 39145, (char) 39273,
            (char) 39228, (char) 39274, (char) 39146, (char) 39275, (char) 39147, (char) 39276, (char) 39149,
            (char) 39277, (char) 39151, (char) 39278, (char) 39154, (char) 39279, (char) 39198, (char) 39280,
            (char) 39166, (char) 39281, (char) 39165, (char) 39282, (char) 39164, (char) 39284, (char) 39156,
            (char) 39285, (char) 39180, (char) 39286, (char) 39250, (char) 39287, (char) 39177, (char) 39290,
            (char) 39171, (char) 39292, (char) 39173, (char) 39293, (char) 39185, (char) 39295, (char) 39187,
            (char) 39296, (char) 39192, (char) 39297, (char) 39186, (char) 39300, (char) 39195, (char) 39301,
            (char) 39201, (char) 39302, (char) 39208, (char) 39304, (char) 39243, (char) 39306, (char) 39231,
            (char) 39307, (char) 39262, (char) 39309, (char) 39235, (char) 39311, (char) 39230, (char) 39312,
            (char) 39240, (char) 39313, (char) 39241, (char) 39314, (char) 39237, (char) 39316, (char) 39244,
            (char) 39532, (char) 39340, (char) 39533, (char) 39341, (char) 39534, (char) 39345, (char) 39535,
            (char) 39348, (char) 39536, (char) 39347, (char) 39537, (char) 39493, (char) 39539, (char) 39361,
            (char) 39540, (char) 39522, (char) 39541, (char) 39380, (char) 39542, (char) 39387, (char) 39543,
            (char) 39391, (char) 39544, (char) 39385, (char) 39545, (char) 39378, (char) 39546, (char) 39478,
            (char) 39547, (char) 39376, (char) 39548, (char) 39389, (char) 39549, (char) 39377, (char) 39550,
            (char) 39381, (char) 39551, (char) 39515, (char) 39552, (char) 39384, (char) 39553, (char) 39501,
            (char) 39554, (char) 32629, (char) 39556, (char) 39509, (char) 39557, (char) 39498, (char) 39558,
            (char) 39409, (char) 39559, (char) 39405, (char) 39560, (char) 39394, (char) 39562, (char) 39530,
            (char) 39563, (char) 39425, (char) 39564, (char) 39511, (char) 39567, (char) 39423, (char) 39568,
            (char) 39439, (char) 39569, (char) 39438, (char) 39570, (char) 39437, (char) 39571, (char) 39429,
            (char) 39574, (char) 39490, (char) 39575, (char) 39449, (char) 39576, (char) 39469, (char) 39578,
            (char) 39479, (char) 39579, (char) 39446, (char) 39580, (char) 39489, (char) 39581, (char) 39470,
            (char) 39582, (char) 39467, (char) 39583, (char) 39480, (char) 39584, (char) 39491, (char) 39585,
            (char) 39486, (char) 39586, (char) 39492, (char) 39587, (char) 39503, (char) 39588, (char) 39519,
            (char) 39589, (char) 39525, (char) 39591, (char) 39524, (char) 39621, (char) 39631, (char) 39627,
            (char) 39638, (char) 39628, (char) 39637, (char) 39699, (char) 39714, (char) 39751, (char) 39768,
            (char) 39753, (char) 39758, (char) 40060, (char) 39770, (char) 40063, (char) 39799, (char) 40065,
            (char) 39791, (char) 40066, (char) 39796, (char) 40069, (char) 40013, (char) 40072, (char) 40056,
            (char) 40075, (char) 39826, (char) 40077, (char) 39825, (char) 40078, (char) 40031, (char) 40080,
            (char) 39824, (char) 40082, (char) 39834, (char) 40084, (char) 39850, (char) 40085, (char) 39838,
            (char) 40090, (char) 40045, (char) 40091, (char) 39851, (char) 40092, (char) 39854, (char) 40094,
            (char) 39895, (char) 40095, (char) 40024, (char) 40096, (char) 39873, (char) 40097, (char) 40058,
            (char) 40098, (char) 39985, (char) 40099, (char) 39993, (char) 40100, (char) 39881, (char) 40101,
            (char) 39971, (char) 40102, (char) 39991, (char) 40103, (char) 39872, (char) 40104, (char) 39882,
            (char) 40105, (char) 39879, (char) 40107, (char) 39933, (char) 40109, (char) 39894, (char) 40110,
            (char) 39914, (char) 40112, (char) 39915, (char) 40113, (char) 39905, (char) 40114, (char) 39908,
            (char) 40115, (char) 39911, (char) 40117, (char) 39906, (char) 40118, (char) 39920, (char) 40119,
            (char) 39899, (char) 40120, (char) 39912, (char) 40123, (char) 39892, (char) 40125, (char) 39944,
            (char) 40131, (char) 39955, (char) 40132, (char) 40055, (char) 40133, (char) 39949, (char) 40134,
            (char) 39954, (char) 40135, (char) 39945, (char) 40140, (char) 39986, (char) 40141, (char) 39981,
            (char) 40142, (char) 39976, (char) 40143, (char) 39973, (char) 40144, (char) 39977, (char) 40147,
            (char) 39987, (char) 40148, (char) 39998, (char) 40149, (char) 40008, (char) 40150, (char) 40009,
            (char) 40151, (char) 39995, (char) 40156, (char) 40022, (char) 40157, (char) 40020, (char) 40158,
            (char) 40023, (char) 40159, (char) 40018, (char) 40162, (char) 40039, (char) 40479, (char) 40165,
            (char) 40480, (char) 40169, (char) 40481, (char) 38622, (char) 40482, (char) 40182, (char) 40483,
            (char) 40180, (char) 40485, (char) 40407, (char) 40486, (char) 40201, (char) 40488, (char) 40199,
            (char) 40489, (char) 40198, (char) 40490, (char) 40227, (char) 40491, (char) 40327, (char) 40492,
            (char) 40469, (char) 40493, (char) 40232, (char) 40495, (char) 40230, (char) 40497, (char) 40223,
            (char) 40498, (char) 40221, (char) 40499, (char) 40219, (char) 40501, (char) 40213, (char) 40502,
            (char) 40421, (char) 40503, (char) 40409, (char) 40504, (char) 40239, (char) 40505, (char) 40240,
            (char) 40506, (char) 40258, (char) 40509, (char) 40255, (char) 40510, (char) 40478, (char) 40511,
            (char) 40251, (char) 40513, (char) 40275, (char) 40514, (char) 40477, (char) 40515, (char) 40273,
            (char) 40516, (char) 40288, (char) 40517, (char) 40285, (char) 40518, (char) 40274, (char) 40519,
            (char) 40436, (char) 40520, (char) 40284, (char) 40521, (char) 40289, (char) 40522, (char) 40306,
            (char) 40524, (char) 40298, (char) 40526, (char) 40303, (char) 40527, (char) 40300, (char) 40529,
            (char) 40329, (char) 40533, (char) 40344, (char) 40535, (char) 40346, (char) 40536, (char) 40379,
            (char) 40542, (char) 40386, (char) 40547, (char) 40380, (char) 40548, (char) 40372, (char) 40550,
            (char) 40474, (char) 40551, (char) 40403, (char) 40552, (char) 40410, (char) 40553, (char) 40431,
            (char) 40554, (char) 40422, (char) 40555, (char) 40434, (char) 40556, (char) 40440, (char) 40557,
            (char) 40442, (char) 40560, (char) 40441, (char) 40563, (char) 40475, (char) 40574, (char) 40570,
            (char) 40614, (char) 40613, (char) 40632, (char) 40617, (char) 40637, (char) 40636, (char) 40644,
            (char) 40643, (char) 40649, (char) 40652, (char) 40681, (char) 40695, (char) 40682, (char) 40690,
            (char) 40702, (char) 40701, (char) 40715, (char) 40703, (char) 40717, (char) 40713, (char) 40761,
            (char) 40756, (char) 40784, (char) 40778, (char) 40785, (char) 40783, (char) 40831, (char) 40786,
            (char) 40832, (char) 40788, (char) 40835, (char) 40799, (char) 40836, (char) 40801, (char) 40837,
            (char) 40793, (char) 40838, (char) 40800, (char) 40839, (char) 40796, (char) 40840, (char) 40806,
            (char) 40841, (char) 40812, (char) 40842, (char) 40810, (char) 40843, (char) 40818, (char) 40844,
            (char) 40823, (char) 40857, (char) 40845, (char) 40858, (char) 40852, (char) 40859, (char) 40853,
            (char) 40863, (char) 40860};
      loadChar(gbToBig5_2);

   }

   private void loadChar(char[] gbBigMap)
   {

      for (int j = 0; j < gbBigMap.length; j += 2)
      {
         char cGb = gbBigMap[j];
         char cBig5 = gbBigMap[j + 1];
         if (cGb >= nCharBase && cGb <= nCharMax)
         {
            cGBToBig5[(int) cGb - nCharBase] = cBig5;
         }
         else
            System.out.println("Can't map :" + cGb + " to " + cBig5);
         if (cBig5 >= nCharBase && cBig5 <= nCharMax)
         {
            cBig5ToGB[(int) cBig5 - nCharBase] = cGb;
         }
         else
            System.out.println("Can't map :" + cBig5 + " to " + cGb);
      }

   }

   /**
    * �˴����뷽��˵���� 
    * �������ڣ�(2002-3-8 10:04:09)
    * 
    * @param args
    *            java.lang.String[]
    */
   public static void main(String[] args)
   {
      System.out.println(ISNConvert.gbToBig5("�����f���Ǵﵽ"));
      System.out.println(ISNConvert.big5ToGb("�����f�҂��_��"));
      System.out.println(ISNConvert.big5ToGb("122ABCDE"));

   }
}