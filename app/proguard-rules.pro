-keep class **Binding { *; }
-keep class * extends androidx.viewbinding.ViewBinding { *; }
-keepclassmembers class * {
    public static *** inflate(android.view.LayoutInflater);
    public static *** inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
    public static *** bind(android.view.View);
}

-keepattributes *Annotation*

-keep class com.bluerose.** { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * extends android.app.Activity { *; }
-keep class * extends androidx.fragment.app.Fragment { *; }