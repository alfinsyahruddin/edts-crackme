//
//  ViewController.swift
//  CrackMeIOS
//
//  Created by M Alfin Syahruddin on 20/02/25.
//

import UIKit
import LocalAuthentication
import Alamofire

class ViewController: UIViewController {
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var enterSerialNumberButton: UIButton!
    @IBOutlet weak var loadingIndicator: UIActivityIndicatorView!
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        if AppState.isProVersion {
            statusLabel.text = "PRO VERSION"
            statusLabel.textColor = .systemGreen
            enterSerialNumberButton.isHidden = true
        } else {
            statusLabel.text = "TRIAL VERSION"
            statusLabel.textColor = .systemGray
            enterSerialNumberButton.isHidden = false
        }
    }
    
    @IBAction func didTapCheckJailbreak(_ sender: Any) {
        let isJailbroken = self.isJailBroken()
        alert(
            isJailbroken ? "JAILBROKEN" : "Not JailBroken",
            isJailbroken ? "⚠️" : "✅"
        )
    }
    
    @IBAction func didTapFetchFromAPIButton(_ sender: Any) {
        loadingIndicator.startAnimating()
        AFSession.request("https://meowfacts.herokuapp.com").responseDecodable(of: CatResponse.self) { response in
            switch response.result {
            case .success(let data):
                self.alert("✅ Success", "\(data.data.first ?? "") 😺")
            case .failure(let error):
                self.alert("❌ Failed", error.localizedDescription)
            }
            self.loadingIndicator.stopAnimating()
        }
    }
    
    @IBAction func didTapUnlockButton(_ sender: Any) {
        authenticate {
            self.alert("✅ UNLOCKED", "Secret Message: '7RU5TM0B1L3T34M'")
        }
    }
    
    private func isJailBroken() -> Bool {
        let paths = [
            "/var/jb/",
            "/var/mobile/Library/Preferences/jailbreak.plist",
            "/var/mobile/Library/Preferences/xyz.willy.Zebra.plist"
        ]
        
        for path in paths {
            if FileManager.default.fileExists(atPath: path) {
                return true
            }
        }
        
        return false
    }
    
    private func authenticate(_ completionHandler: @escaping () -> Void) {
        let context = LAContext()
        var error: NSError?
        
        if context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error) {
            let reason = "Identify yourself!"
            
            context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason) { success, authenticationError in
                
                DispatchQueue.main.async {
                    if success {
                        completionHandler()
                    } else {
                        self.alert("❌ Authentication failed", "You could not be verified; please try again.")
                    }
                }
            }
        } else {
            alert("❌ Biometry unavailable", "Your device is not configured for biometric authentication.")
        }
    }
}

struct CatResponse: Codable {
    var data: [String]
}

let AFSession = Session(
    serverTrustManager: ServerTrustManager(
        evaluators: [
            "meowfacts.herokuapp.com": PublicKeysTrustEvaluator()
        ]
    )
)

extension UIViewController {
    func alert(_ title: String, _ message: String) {
        let ac = UIAlertController(title: title, message: message, preferredStyle: .alert)
        ac.addAction(UIAlertAction(title: "OK", style: .default))
        self.present(ac, animated: true)
    }
}

class AppState {
   static var isProVersion = false
}

