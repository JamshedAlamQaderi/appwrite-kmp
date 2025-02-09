package com.jamshedalamqaderi.kmp.appwrite

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ExportObjCClass
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIDevice
import platform.UIKit.UIViewController
import platform.WebKit.WKNavigationAction
import platform.WebKit.WKNavigationActionPolicy
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(BetaInteropApi::class)
@ExportObjCClass
class OAuthWebViewController(
    private val authUrl: String,
    private val callbackScheme: String,
    private val onResult: (String?, String?) -> Unit,
) : UIViewController(nibName = null, bundle = null), WKNavigationDelegateProtocol {
    private lateinit var webView: WKWebView

    @OptIn(ExperimentalForeignApi::class)
    override fun viewDidLoad() {
        super.viewDidLoad()

        // Create and configure WKWebView.
        val config = WKWebViewConfiguration()
        webView = WKWebView(frame = view.bounds, configuration = config)
        webView.customUserAgent = getSafariUserAgent()
        webView.navigationDelegate = this
        view.addSubview(webView)

        // Load the OAuth login URL.
        val url = NSURL.URLWithString(authUrl)
        if (url == null) {
            onResult(null, "Invalid auth URL")
            dismissViewControllerAnimated(true, null)
            return
        }
        val request = NSURLRequest.requestWithURL(url)
        webView.loadRequest(request)
    }

    private fun getSafariUserAgent(): String {
        val systemName = UIDevice.currentDevice.systemName
        val systemVersion = UIDevice.currentDevice.systemVersion
        val model = UIDevice.currentDevice.model
        return "Mozilla/5.0 ($model; CPU $systemName ${
            systemVersion.replace(
                ".",
                "_",
            )
        } like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/605.1.15"
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun viewWillLayoutSubviews() {
        super.viewWillLayoutSubviews()
        webView.setFrame(view.bounds)
    }

    // WKNavigationDelegate method: Intercept navigation actions.
    override fun webView(
        webView: WKWebView,
        decidePolicyForNavigationAction: WKNavigationAction,
        decisionHandler: (WKNavigationActionPolicy) -> Unit,
    ) {
        val requestURL = decidePolicyForNavigationAction.request.URL
        if (requestURL != null && requestURL.absoluteString?.startsWith(callbackScheme) == true) {
            // We found the callback URL.
            onResult(requestURL.absoluteString, null)
            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyCancel)
            dismissViewControllerAnimated(true, null)
        } else {
            decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)
        }
    }
}
