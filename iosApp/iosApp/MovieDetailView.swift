//
//  MovieDetailView.swift
//  iosApp
//

import SwiftUI
import ComposeApp

struct MovieDetailView: View {

    let movieId: Int32

    @StateObject private var viewModel = MovieDetailViewModel()

    private let backgroundColor = Color(red: 0.07, green: 0.08, blue: 0.13)

    var body: some View {
        ZStack {
            backgroundColor
                .ignoresSafeArea()

            content
        }
        .task {
            viewModel.load(movieId: movieId)
        }
    }

    @ViewBuilder
    private var content: some View {
        if viewModel.isLoading {
            loadingView
        } else if let errorMessage = viewModel.errorMessage {
            errorView(message: errorMessage)
        } else if let movie = viewModel.movie {
            movieContent(movie)
        } else {
            Text("Sin información")
                .foregroundStyle(.white)
        }
    }

    private var loadingView: some View {
        VStack(spacing: 12) {
            ProgressView()
                .tint(.white)

            Text("Cargando película...")
                .foregroundStyle(.white)
                .font(.body)
        }
    }

    private func errorView(message: String) -> some View {
        VStack(spacing: 12) {
            Text("Ocurrió un error")
                .font(.headline)
                .foregroundStyle(.white)

            Text(message)
                .font(.subheadline)
                .foregroundStyle(.gray)
                .multilineTextAlignment(.center)
        }
        .padding(24)
    }

    private func movieContent(_ movie: MovieDetailUiModel) -> some View {
        GeometryReader { geometry in
            ScrollView(.vertical, showsIndicators: false) {
                VStack(alignment: .leading, spacing: 0) {

                    headerImage(movie: movie, width: geometry.size.width)

                    VStack(alignment: .leading, spacing: 18) {

                        Text(movie.title)
                            .font(.system(size: 30, weight: .bold))
                            .foregroundStyle(.white)
                            .multilineTextAlignment(.leading)
                            .lineLimit(nil)
                            .fixedSize(horizontal: false, vertical: true)
                            .frame(maxWidth: .infinity, alignment: .leading)

                        HStack(spacing: 8) {
                            infoChip(text: movie.releaseDate)
                            infoChip(text: movie.duration)
                            infoChip(text: "⭐ \(movie.rating)")
                        }
                        .frame(maxWidth: .infinity, alignment: .leading)

                        detailCard(
                            title: "Título original",
                            text: movie.originalTitle
                        )

                        detailCard(
                            title: "Descripción",
                            text: movie.overview
                        )
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 20)
                    .padding(.bottom, 32)
                    .frame(width: geometry.size.width, alignment: .leading)
                }
                .frame(width: geometry.size.width, alignment: .leading)
            }
            .background(backgroundColor)
        }
    }

    private func headerImage(movie: MovieDetailUiModel, width: CGFloat) -> some View {
        AsyncImage(url: URL(string: movie.backdropUrl)) { phase in
            switch phase {
            case .empty:
                Rectangle()
                    .fill(Color.gray.opacity(0.25))
                    .overlay {
                        ProgressView()
                            .tint(.white)
                    }

            case .success(let image):
                image
                    .resizable()
                    .scaledToFill()

            case .failure:
                Rectangle()
                    .fill(Color.gray.opacity(0.25))
                    .overlay {
                        Text("Imagen no disponible")
                            .font(.subheadline)
                            .foregroundStyle(.white.opacity(0.8))
                    }

            @unknown default:
                Rectangle()
                    .fill(Color.gray.opacity(0.25))
            }
        }
        .frame(width: width, height: 280)
        .clipped()
        .overlay(alignment: .bottom) {
            LinearGradient(
                colors: [
                    .clear,
                    backgroundColor
                ],
                startPoint: .top,
                endPoint: .bottom
            )
            .frame(height: 130)
        }
    }

    private func infoChip(text: String) -> some View {
        Text(text.isEmpty ? "--" : text)
            .font(.system(size: 14, weight: .medium))
            .foregroundStyle(.white)
            .padding(.horizontal, 10)
            .padding(.vertical, 7)
            .background(Color.white.opacity(0.14))
            .clipShape(RoundedRectangle(cornerRadius: 9))
    }

    private func detailCard(title: String, text: String) -> some View {
        VStack(alignment: .leading, spacing: 10) {
            Text(title)
                .font(.system(size: 17, weight: .semibold))
                .foregroundStyle(.white)
                .frame(maxWidth: .infinity, alignment: .leading)

            Text(text.isEmpty ? "Información no disponible" : text)
                .font(.system(size: 16))
                .foregroundStyle(.white.opacity(0.84))
                .lineSpacing(4)
                .multilineTextAlignment(.leading)
                .fixedSize(horizontal: false, vertical: true)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color.white.opacity(0.08))
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}
