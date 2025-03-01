# Commands 🖥️

## Android

### Install APK

```
adb install -r CrackMeAndroid.apk
```

### Bypass Root

```
frida --codeshare Q0120S/root-detection-bypass -U -n CrackMeAndroid
```

### Bypass SSL Pinning

```
objection -g CrackMeAndroid explore
android sslpinning bypass
```

### Extract APK

```
apktool d CrackMeAndroid.apk -o CrackMeAndroid
```
### Repacking APK

```
apktool b CrackMeAndroid -o modified.apk
```

### Resigning APK

```
java -jar ../signer.jar --apk modified.apk
```

---

## iOS

### Bypass Jailbreak

```
frida-trace -U -n CrackMeIOS -i "*jailbroken*/i"
frida -U -n CrackMeIOS -l hook.js
```

### Bypass SSL Pinning

```
objection -g CrackMeIOS explore
ios sslpinning bypass
```

### Bypass Touch ID / Face ID

```
objection -g CrackMeIOS explore
ios ui biometrics_bypass
```

### Dump Decrypted IPA File

```
sudo pip3 install -r requirements.txt --upgrade
iproxy 2222 22
python3 dump.py -o CrackMeIOS.ipa -u root -P root CrackMeIOS
```

