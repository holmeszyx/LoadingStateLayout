LoadingStateLayout
==================

A layout with loading state. You can easily to show loading state(waiting state) on you working view.


How to use
----------

LoadingStateLayout is the base layout for all LoadingState views.
In most cases, we just need to use views which under the z.hol.loadingstate.view package.

The class start with 'Simple' like 'SimpleViewWithLoadingState', means you need set custom layout in xml
with 'DataView' attribute.(See the sample).

There are other attributes you can set in style.

    EmptyText
    EmptyIcon
    ErrorText
    ErrorIcon
    LoadingProgress (the circle drawable)

you can also set above attribute in theme with name 'loadingStateStyle' 

1. create a custom theme for loading state views

    <style name="CustomLoadingState">
        <item name="LoadingProgress">@drawable/progress_loading</item>
        <item name="EmptyIcon">@drawable/icon_empty</item>
    </style>
    
2. set the 'CustomeLoadingState' theme in you application theme, with name 'loadingStateStyle'

    <style name="AppTheme" parent="AppBaseTheme">
        <item name="loadingStateStyle">@style/CustomLoadingState</item>
    </style>
    
    