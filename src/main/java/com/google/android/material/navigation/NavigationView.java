package com.google.android.material.navigation;
import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import r.android.view.Gravity;
import r.android.view.MenuItem;
import r.android.view.View;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
public class NavigationView extends ScrimInsetsFrameLayout {
  private static final int PRESENTER_NAVIGATION_VIEW_ID=1;
  private final NavigationMenu menu;
  private final NavigationMenuPresenter presenter=new NavigationMenuPresenter();
  OnNavigationItemSelectedListener listener;
  private final int maxWidth;
  private boolean topInsetScrimEnabled=true;
  private boolean bottomInsetScrimEnabled=true;
  private int drawerLayoutCornerSize=0;
  private final boolean drawerLayoutCornerSizeBackAnimationEnabled;
  private final int drawerLayoutCornerSizeBackAnimationMax;
  public void setNavigationItemSelectedListener(  OnNavigationItemSelectedListener listener){
    this.listener=listener;
  }
  protected void onMeasure(  int widthSpec,  int heightSpec){
switch (r.android.view.View.MeasureSpec.getMode(widthSpec)) {
case r.android.view.View.MeasureSpec.EXACTLY:
      break;
case r.android.view.View.MeasureSpec.AT_MOST:
    widthSpec=r.android.view.View.MeasureSpec.makeMeasureSpec(Math.min(r.android.view.View.MeasureSpec.getSize(widthSpec),maxWidth),r.android.view.View.MeasureSpec.EXACTLY);
  break;
case r.android.view.View.MeasureSpec.UNSPECIFIED:
widthSpec=r.android.view.View.MeasureSpec.makeMeasureSpec(maxWidth,r.android.view.View.MeasureSpec.EXACTLY);
break;
}
super.onMeasure(widthSpec,heightSpec);
}
public void inflateMenu(com.ashera.widget.HasWidgets parent, String json, com.ashera.core.IFragment fragment){
presenter.setUpdateSuspended(true);
androidx.appcompat.view.menu.MenuParser.parseMenu(parent, menu, json, fragment);//getMenuInflater().inflate(resId,menu);
presenter.setUpdateSuspended(false);
presenter.updateMenuView(false);
}
public void addHeaderView(View view){
presenter.addHeaderView(view);
}
public void removeHeaderView(View view){
presenter.removeHeaderView(view);
}
public View getHeaderView(int index){
return presenter.getHeaderView(index);
}
public ColorStateList getItemIconTintList(){
return presenter.getItemTintList();
}
public void setItemIconTintList(ColorStateList tint){
presenter.setItemIconTintList(tint);
}
public ColorStateList getItemTextColor(){
return presenter.getItemTextColor();
}
public void setItemTextColor(ColorStateList textColor){
presenter.setItemTextColor(textColor);
}
public Drawable getItemBackground(){
return presenter.getItemBackground();
}
public void setItemBackground(Drawable itemBackground){
presenter.setItemBackground(itemBackground);
}
public int getItemHorizontalPadding(){
return presenter.getItemHorizontalPadding();
}
public void setItemHorizontalPadding(int padding){
presenter.setItemHorizontalPadding(padding);
}
public int getItemVerticalPadding(){
return presenter.getItemVerticalPadding();
}
public void setItemVerticalPadding(int padding){
presenter.setItemVerticalPadding(padding);
}
public int getItemIconPadding(){
return presenter.getItemIconPadding();
}
public void setItemIconPadding(int padding){
presenter.setItemIconPadding(padding);
}
public void setItemTextAppearance(String resId){
presenter.setItemTextAppearance(resId);
}
public void setItemIconSize(int iconSize){
presenter.setItemIconSize(iconSize);
}
public void setItemMaxLines(int itemMaxLines){
presenter.setItemMaxLines(itemMaxLines);
}
public int getItemMaxLines(){
return presenter.getItemMaxLines();
}
public void setDividerInsetStart(int dividerInsetStart){
presenter.setDividerInsetStart(dividerInsetStart);
}
public void setDividerInsetEnd(int dividerInsetEnd){
presenter.setDividerInsetEnd(dividerInsetEnd);
}
public void setSubheaderInsetStart(int subheaderInsetStart){
presenter.setSubheaderInsetStart(subheaderInsetStart);
}
public void setSubheaderInsetEnd(int subheaderInsetEnd){
presenter.setSubheaderInsetEnd(subheaderInsetEnd);
}
public interface OnNavigationItemSelectedListener {
public boolean onNavigationItemSelected(MenuItem item);
}
public void initNavigationView(){
presenter.setId(PRESENTER_NAVIGATION_VIEW_ID);
presenter.initForMenu(getContext(),this.menu);
menu.addMenuPresenter(presenter);
presenter.getMenuView(this);
}
public NavigationView(){
this.menu=new NavigationMenu(getContext());
drawerLayoutCornerSize=0;
drawerLayoutCornerSizeBackAnimationEnabled=drawerLayoutCornerSize == 0;
maxWidth=0;
drawerLayoutCornerSizeBackAnimationMax=0;
setItemHorizontalPadding((int)com.ashera.widget.PluginInvoker.convertDpToPixel("22dp"));
setItemIconPadding((int)com.ashera.widget.PluginInvoker.convertDpToPixel("14dp"));
setSubheaderInsetStart((int)com.ashera.widget.PluginInvoker.convertDpToPixel("22dp"));
setSubheaderInsetEnd((int)com.ashera.widget.PluginInvoker.convertDpToPixel("22dp"));
this.menu.setCallback(new androidx.appcompat.view.menu.MenuBuilder.Callback(){
@Override public boolean onMenuItemSelected(androidx.appcompat.view.menu.MenuBuilder menu,MenuItem item){
return listener != null && listener.onNavigationItemSelected(item);
}
@Override public void onMenuModeChange(androidx.appcompat.view.menu.MenuBuilder menu){
}
}
);
}
public NavigationMenu getMenu(){
return this.menu;
}
}
