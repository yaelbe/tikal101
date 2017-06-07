package com.tikal.movies.view.mvp.view;

import java.util.List;

import com.tikal.movies.view.mvp.model.ImageModel;

public interface GalleryView extends LoadDataView {
    void renderImages(List<ImageModel> images);
    void clearImages();
}
