var method = Module.findExportByName(null, "$s10CrackMeIOS14ViewControllerC12isJailBroken33_FB20385B5A89CAA613E4DEF458A98C2DLLSbyF");

if (method) {
  Interceptor.attach(method, {
    onEnter: function(_args) {
      console.log("Hooked Swift method: isJailbroken");
      // You can inspect or modify args here
    },
    onLeave: function(retval) {
      console.log("Original Swift return value:", retval.toInt32());

      // Modify the return value to 'false' (which is 0)
      retval.replace(0);

      console.log("Modified Swift return value to false (0)");
    }
  });
} else {
  console.log("Hooking Swift method failed!");
}
