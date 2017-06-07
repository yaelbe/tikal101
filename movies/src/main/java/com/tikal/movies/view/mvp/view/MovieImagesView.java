package com.tikal.movies.view.mvp.view;

import com.tikal.movies.view.mvp.model.ImageModel;

import java.util.List;

public interface MovieImagesView extends LoadDataView {
    void renderTabs(List<ImageModel> backdrops, List<ImageModel> posters);
}
