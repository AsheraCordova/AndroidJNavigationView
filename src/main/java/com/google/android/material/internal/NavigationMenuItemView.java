package com.google.android.material.internal;
import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import androidx.appcompat.view.menu.MenuItemImpl;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.FrameLayout;
public class NavigationMenuItemView extends ForegroundLinearLayout {
  private int iconSize;
  private boolean needsEmptyIcon;
  boolean checkable;
  boolean isBold=true;
  private  r.android.widget.TextView textView;
  private FrameLayout actionArea;
  private MenuItemImpl itemData;
  private ColorStateList iconTintList;
  private boolean hasIconTintList;
  private Drawable emptyDrawable;
  public void initialize(  MenuItemImpl itemData,  int menuType){
    this.itemData=itemData;
    if (itemData.getItemId() > 0) {
      setId(itemData.getItemId());
    }
    setVisibility(itemData.isVisible() ? VISIBLE : GONE);
    if (getBackground() == null) {
      //ViewCompat.setBackground(this,createDefaultBackground());
    }
    setCheckable(itemData.isCheckable());
    setChecked(itemData.isChecked());
    setEnabled(itemData.isEnabled());
    setTitle(itemData.getTitle());
    setIcon(itemData.getIcon());
    setActionView(itemData.getActionView());
    //setContentDescription(itemData.getContentDescription());
    //TooltipCompat.setTooltipText(this,itemData.getTooltipText());
    adjustAppearance();
  }
  public void initialize(  MenuItemImpl itemData,  boolean isBold){
    this.isBold=isBold;
    initialize(itemData,0);
  }
  private boolean shouldExpandActionArea(){
    return itemData.getTitle() == null && itemData.getIcon() == null && itemData.getActionView() != null;
  }
  private void adjustAppearance(){
    if (shouldExpandActionArea()) {
      textView.setVisibility(View.GONE);
      if (actionArea != null) {
        LayoutParams params=(LayoutParams)actionArea.getLayoutParams();
        params.width=LayoutParams.MATCH_PARENT;
        actionArea.setLayoutParams(params);
      }
    }
 else {
      textView.setVisibility(View.VISIBLE);
      if (actionArea != null) {
        LayoutParams params=(LayoutParams)actionArea.getLayoutParams();
        params.width=LayoutParams.WRAP_CONTENT;
        actionArea.setLayoutParams(params);
      }
    }
  }
  public void recycle(){
    if (actionArea != null) {
      actionArea.removeAllViews();
    }
    //textView.setCompoundDrawables(null,null,null,null);
  }
  private void setActionView(  View actionView){
    if (actionView != null) {
      if (actionArea == null) {
        //actionArea=(FrameLayout)((ViewStub)findViewById(R.id.design_menu_item_action_area_stub)).inflate();
      }
      if (actionView.getParent() != null) {
        ((ViewGroup)actionView.getParent()).removeView(actionView);
      }
      actionArea.removeAllViews();
      actionArea.addView(actionView);
    }
  }
  public MenuItemImpl getItemData(){
    return itemData;
  }
  public void setTitle(  CharSequence title){
    textView.setMyAttribute("text",title);
  }
  public void setCheckable(  boolean checkable){
    refreshDrawableState();
    if (this.checkable != checkable) {
      this.checkable=checkable;
      //accessibilityDelegate.sendAccessibilityEvent(textView,AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED);
    }
  }
  public void setChecked(  boolean checked){
    refreshDrawableState();
    //textView.setChecked(checked);
    //textView.setTypeface(textView.getTypeface(),checked && isBold ? Typeface.BOLD : Typeface.NORMAL);
  }
  public void setIcon(  Drawable icon){
    if (icon != null) {
      icon.setRecycleable(true);if (hasIconTintList) {
        //Drawable.ConstantState state=icon.getConstantState();
        //icon=DrawableCompat.wrap(state == null ? icon : state.newDrawable()).mutate();
        icon.setTintColor(iconTintList);//DrawableCompat.setTintList(icon,iconTintList);
      }
      icon.setBounds(0,0,iconSize,iconSize);textView.setMyAttribute("drawableIconSize", iconSize);
    }
 else     if (needsEmptyIcon) {
      if (emptyDrawable == null) {
        //emptyDrawable=ResourcesCompat.getDrawable(getResources(),R.drawable.navigation_empty_icon,getContext().getTheme());
        if (emptyDrawable != null) {
          emptyDrawable.setBounds(0,0,iconSize,iconSize);
        }
      }
      icon=emptyDrawable;
    }
    if (icon != null) {textView.setMyAttribute("drawableStart", icon);textView.setMyAttribute("drawableTint", iconTintList);}//TextViewCompat.setCompoundDrawablesRelative(textView,icon,null,null,null);
  }
  public void setIconSize(  int iconSize){
    this.iconSize=iconSize;
  }
  void setIconTintList(  ColorStateList tintList){
    iconTintList=tintList;
    hasIconTintList=iconTintList != null;
    if (itemData != null) {
      setIcon(itemData.getIcon());
    }
  }
  public void setTextAppearance(  String textAppearance){
    textView.setMyAttribute("textAppearance",textAppearance);//TextViewCompat.setTextAppearance(textView,textAppearance);
  }
  public void setTextColor(  ColorStateList colors){
    textView.setMyAttribute("textColor",colors);
  }
  public void setNeedsEmptyIcon(  boolean needsEmptyIcon){
    this.needsEmptyIcon=needsEmptyIcon;
  }
  public void setIconPadding(  int padding){
    textView.setMyAttribute("drawablePadding", padding);//textView.setCompoundDrawablePadding(padding);
  }
  public void setMaxLines(  int maxLines){
    textView.setMyAttribute("maxLines",maxLines);
  }
  public void initNavigationMenuItemView(){
    inflateView("@layout/design_navigation_menu_item_new");
    setIconSize((int)com.ashera.widget.PluginInvoker.convertDpToPixel("24dp"));
    textView=findViewById(com.ashera.widget.IdGenerator.getId("@+id/design_menu_item_text"));
    textView.setDuplicateParentStateEnabled(true);
  }
}
