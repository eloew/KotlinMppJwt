//
//  ViewController.swift
//  KotlinMppJwtIos
//
//  Created by Eric Loew on 6/28/20.
//  Copyright © 2020 erl. All rights reserved.
//

import UIKit
import SharedCode


class ViewController: UIViewController, EchoPresenterEchoView {
    
    //MARK: - Outlets

    @IBOutlet weak var errorMessageOutlet: UILabel!
    @IBOutlet weak var sharedMessageOutlet: UILabel!
    @IBOutlet weak var ktorMessageOutlet: UILabel!
    
    //MARK: - Fields
    private lazy var echoPresenter: EchoPresenter = {
           EchoPresenter (
               uiContext: UI() as KotlinCoroutineContext,
               view: self
           )
       }()
    
    //MARK: - overloads
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        

        errorMessageOutlet.text = ""
        ktorMessageOutlet.text = ""
        sharedMessageOutlet.text = CommonKt.createApplicationScreenMessage()
        
        let tkn = Token()
        let token = tkn.getToken(email: "KotlinMppJwt@hotmail.com")
        echoPresenter.getEchoMessage(token: token, message: "Ktor Rocks with iOS!")
        
    }
    
    //MARK: - EchoPresenterEchoView
    func onEcho(message: String) {
        ktorMessageOutlet.text = message
    }
    
    func showError(message: String) {
        errorMessageOutlet.text = message
    }
    
    func showError(error: KotlinThrowable) {
        if let message = error.message {
             errorMessageOutlet.text = message
        }
    }

}

