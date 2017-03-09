package com.nickromero.seniorproject.views;

/**
 * Created by nickromero on 2/18/17.
 */

/**
 * The BaseView is used to set up the MVP pattern
 */
public interface BaseView<T> {
    void setPresenter(T presenter);
}
