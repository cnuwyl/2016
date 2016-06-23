package loadlib;

public class LoadLib {
		public static native String SegmentALine(String line);
		public static native boolean Init (String file);
		static{
			System.loadLibrary("LoadLib");  //共享库的加载放到了/usr/local/lib/so/libLoadLib.so,已将它链接到/usr/lib/下面
			
		}
}
