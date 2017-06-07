package com.tikal.movies.view.mapper;


import com.tikal.movies.data.BaseMapper;
import com.tikal.movies.data.entity.Image;
import com.tikal.movies.view.helper.Utils;
import com.tikal.movies.view.mvp.model.ImageModel;

public class ImageMapper extends BaseMapper<Image, ImageModel>{

    private String size;

    public ImageMapper(String size) {
        this.size = size;
    }

    @Override
    public ImageModel toModel(Image entity) {
        ImageModel model = new ImageModel();

        model.setOriginalURL(Utils.buildCompleteImageURL(entity.getFile_path(), "original"));
        model.setUrl(Utils.buildCompleteImageURL(entity.getFile_path(), size));

        return model;
    }

    @Override
    public ImageModel deserializeModel(String serializedModel) {
        return gson.fromJson(serializedModel, ImageModel.class);
    }
}
