package com.demo.jni;

public class ForkJni {
		public static native String SegmentALine(String line);
		static{
			System.loadLibrary("ForkJni");
		}
}
