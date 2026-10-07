# iCalc

تطبيق آلة حاسبة Android  باسم **iCalc**، بتصميم  خلفية فاتحة، لوحة سوداء/كريمية، وأزرار عمليات برتقالية دافئة.

## الوظائف

العمليات الأساسية، النسبة المئوية، الفاصلة العشرية، تغيير الإشارة، المسح الكامل، وحذف آخر رقم. التطبيق يعمل بالكامل بدون إنترنت أو صلاحيات خاصة.

## بناء APK

يتطلب Android SDK 34 وGradle 8.6:

```bash
ANDROID_HOME=/path/to/android-sdk ./gradlew assembleDebug
```

الناتج يكون داخل `app/build/outputs/apk/debug/app-debug.apk`.
