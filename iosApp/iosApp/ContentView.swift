import SwiftUI

import SwiftUI
import UIKit
import ComposeApp

struct ComposeRootView: UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // No se necesita actualizar nada por ahora.
    }
}

struct ContentView: View {
    var body: some View {
        ComposeRootView()
            .ignoresSafeArea()
    }
}
