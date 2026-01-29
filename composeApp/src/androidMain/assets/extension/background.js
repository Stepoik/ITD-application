const NATIVE_APP = "cookie_bridge";

let port = browser.runtime.connectNative(NATIVE_APP);
port.onMessage.addListener(async message => {
    try {
      const cookies = await browser.cookies.getAll({
        domain: "xn--d1ah4a.com"
      });

      port.postMessage({
        type: "COOKIES",
        cookies: cookies.map(c => ({
          name: c.name,
          value: c.value,
          domain: c.domain,
          path: c.path,
          httpOnly: c.httpOnly,
          secure: c.secure,
          sameSite: c.sameSite
        }))
      });

    } catch (e) {
      console.error("GET_COOKIES failed", e);
      port.postMessage({
        type: "ERROR",
        message: String(e)
      });
    }
});