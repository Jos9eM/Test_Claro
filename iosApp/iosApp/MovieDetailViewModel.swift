//
//  MovieDetailViewModel.swift
//  iosApp
//
//  Created by Christopher Guarneros Diaz on 18/05/26.
//

import Foundation
import ComposeApp

@MainActor
final class MovieDetailViewModel: ObservableObject {

    @Published var isLoading: Bool = false
    @Published var movie: MovieDetailUiModel?
    @Published var errorMessage: String?

    private let bridge = MovieDetailBridge()

    func load(movieId: Int32) {
        isLoading = true
        errorMessage = nil

        bridge.fetchMovieDetail(
            movieId: movieId,
            onSuccess: { [weak self] movie in
                DispatchQueue.main.async {
                    self?.movie = movie
                    self?.isLoading = false
                }
            },
            onError: { [weak self] error in
                DispatchQueue.main.async {
                    self?.errorMessage = error
                    self?.isLoading = false
                }
            }
        )
    }

    deinit {
        bridge.clear()
    }
}
