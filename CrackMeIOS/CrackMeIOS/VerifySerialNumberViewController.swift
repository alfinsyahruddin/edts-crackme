//
//  VerifySerialNumberViewController.swift
//  CrackMeIOS
//
//  Created by M Alfin Syahruddin on 26/02/25.
//

import UIKit

class VerifySerialNumberViewController: UIViewController {

    @IBOutlet weak var textField: UITextField!
    
    @IBAction func didTapVerifyButton(_ sender: Any) {
        let isValid = verifySerialNumber(textField.text ?? "")
        if isValid {
            AppState.isProVersion = true
            self.navigationController?.popViewController(animated: true)
            alert("✅ Success", "Valid Serial Number")
        } else {
            alert("❌ Failed", "Invalid Serial Number")
        }
    }
    
    private func verifySerialNumber(_ serial: String) -> Bool {
        let validSerialNumber = hexToString("3132332D313233")
        let isValid = serial == validSerialNumber
        return isValid
    }
}

func hexToString(_ hex: String) -> String? {
    guard hex.count % 2 == 0 else { return nil }
    
    var bytes = [UInt8]()
    var startIndex = hex.startIndex
    
    while startIndex < hex.endIndex {
        let endIndex = hex.index(startIndex, offsetBy: 2)
        let byteString = hex[startIndex..<endIndex]
        
        if let byte = UInt8(byteString, radix: 16) {
            bytes.append(byte)
        } else {
            return nil
        }
        
        startIndex = endIndex
    }
    
    return String(bytes: bytes, encoding: .utf8)
}

