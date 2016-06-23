package FakeSegmentForJni;

public class FakeSegmentForJni {
		public static native String SegmentALine(String line);
		static{
			System.loadLibrary("FakeSegmentForJni");
		}
}
