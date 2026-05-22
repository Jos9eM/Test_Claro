import SwiftUI
import UIKit
import ComposeApp

struct SelectedMovie: Identifiable {
    let id: Int32
}

struct ComposeRootView: UIViewControllerRepresentable {

    let onMovieSelected: (Int32) -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        MovieDetailNavigationBridge.shared.setOnOpenMovieDetail { movieId in
            DispatchQueue.main.async {
                onMovieSelected(Int32(movieId))
            }
        }

        return MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
    }

    static func dismantleUIViewController(_ uiViewController: UIViewController, coordinator: ()) {
        MovieDetailNavigationBridge.shared.clearOnOpenMovieDetail()
    }
}

struct ContentView: View {

    @State private var selectedMovie: SelectedMovie?

    var body: some View {
        ComposeRootView { movieId in
            selectedMovie = SelectedMovie(id: movieId)
        }
        .ignoresSafeArea()
        .fullScreenCover(item: $selectedMovie) { movie in
            MovieDetailView(movieId: movie.id)
        }
    }
}
