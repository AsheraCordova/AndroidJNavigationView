package com.google.android.material.navigation;
import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import androidx.appcompat.view.menu.MenuBuilder;
import r.android.view.MenuItem;
import r.android.widget.FrameLayout;
import com.google.android.material.badge.BadgeDrawable;
public abstract class NavigationBarView extends FrameLayout {
  public static final int LABEL_VISIBILITY_AUTO=-1;
  public static final int LABEL_VISIBILITY_SELECTED=0;
  public static final int LABEL_VISIBILITY_LABELED=1;
  public static final int LABEL_VISIBILITY_UNLABELED=2;
  private static final int MENU_PRESENTER_ID=1;
  private NavigationBarMenu menu;
  private NavigationBarMenuView menuView;
  private NavigationBarPresenter presenter=new NavigationBarPresenter();
  private ColorStateList itemRippleColor;
  private OnItemSelectedListener selectedListener;
  private OnItemReselectedListener reselectedListener;
  public void setOnItemSelectedListener(  OnItemSelectedListener listener){
    selectedListener=listener;
  }
  public void setOnItemReselectedListener(  OnItemReselectedListener listener){
    reselectedListener=listener;
  }
  public NavigationBarMenu getMenu(){
    return menu;
  }
  public NavigationBarMenuView getMenuView(){
    return menuView;
  }
  public void inflateMenu(com.ashera.widget.HasWidgets parent, String json, com.ashera.core.IFragment fragment){
    presenter.setUpdateSuspended(true);
    androidx.appcompat.view.menu.MenuParser.parseMenu(parent, menu, json, fragment);//getMenuInflater().inflate(resId,menu);
    presenter.setUpdateSuspended(false);
    presenter.updateMenuView(true);
  }
  public void setItemIconTintList(  ColorStateList tint){
    menuView.setIconTintList(tint);
  }
  public void setItemIconSize(  int iconSize){
    menuView.setItemIconSize(iconSize);
  }
  public void setItemTextColor(  ColorStateList textColor){
    menuView.setItemTextColor(textColor);
  }
  public void setItemBackground(  Drawable background){
    menuView.setItemBackground(background);
    itemRippleColor=null;
  }
  public int getSelectedItemId(){
    return menuView.getSelectedItemId();
  }
  public void setSelectedItemId(  int itemId){
    MenuItem item=menu.findItem(itemId);
    if (item != null) {
      if (!menu.performItemAction(item,presenter,0)) {
        item.setChecked(true);
      }
    }
  }
  public void setLabelVisibilityMode(  int labelVisibilityMode){
    if (menuView.getLabelVisibilityMode() != labelVisibilityMode) {
      menuView.setLabelVisibilityMode(labelVisibilityMode);
      presenter.updateMenuView(false);
    }
  }
  public void setItemTextAppearanceInactive(  String textAppearanceRes ){
    menuView.setItemTextAppearanceInactive(textAppearanceRes);
  }
  public void setItemTextAppearanceActive(  String textAppearanceRes ){
    menuView.setItemTextAppearanceActive(textAppearanceRes);
  }
  public BadgeDrawable getOrCreateBadge(  int menuItemId){
    return menuView.getOrCreateBadge(menuItemId);
  }
public interface OnItemSelectedListener {
    boolean onNavigationItemSelected(    MenuItem item);
  }
public interface OnItemReselectedListener {
    void onNavigationItemReselected(    MenuItem item);
  }
  public abstract int getMaxItemCount();
  protected abstract NavigationBarMenuView createNavigationBarMenuView(  Context context);
  protected NavigationBarPresenter getPresenter(){
    return presenter;
  }
  @Override public void requestLayout(){
    super.requestLayout();
    if (menuView != null) {
      menuView.requestLayout();
    }
  }
  public void initNavigationBarView(){
    this.menu=new NavigationBarMenu(this.getClass(),getMaxItemCount());
    menuView=createNavigationBarMenuView(getContext());
    presenter.setMenuView(menuView);
    presenter.setId(MENU_PRESENTER_ID);
    menuView.setPresenter(presenter);
    this.menu.addMenuPresenter(presenter);
    presenter.initForMenu(getContext(),this.menu);
    menuView.setLabelVisibilityMode(LABEL_VISIBILITY_AUTO);
    menuView.setItemIconSize((int)com.ashera.widget.PluginInvoker.convertDpToPixel("24dp"));
    menuView.setIconTintList(menuView.createDefaultColorStateList(0));
    this.menu.setCallback(new MenuBuilder.Callback(){
      @Override public boolean onMenuItemSelected(      MenuBuilder menu,      MenuItem item){
        if (reselectedListener != null && item.getItemId() == getSelectedItemId()) {
          reselectedListener.onNavigationItemReselected(item);
          return true;
        }
        return selectedListener != null && !selectedListener.onNavigationItemSelected(item);
      }
      @Override public void onMenuModeChange(      MenuBuilder menu){
      }
    }
);
  }
}
