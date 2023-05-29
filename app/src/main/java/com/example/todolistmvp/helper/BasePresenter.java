package com.example.todolistmvp.helper;

public interface BasePresenter<T extends BaseView> {
    void onAttach(T view);

    void onDetach();
}
