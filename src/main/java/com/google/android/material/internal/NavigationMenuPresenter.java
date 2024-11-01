package com.google.android.material.internal;
import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.content.res.Resources;
import r.android.graphics.drawable.Drawable;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.recyclerview.widget.RecyclerView;
import r.android.view.View;
import r.android.view.ViewGroup;
import r.android.widget.LinearLayout;
import r.android.widget.TextView;
import java.util.ArrayList;
public class NavigationMenuPresenter implements MenuPresenter {
  public static final int NO_TEXT_APPEARANCE_SET=0;
  private androidx.recyclerview.widget.RecyclerView menuView;
  LinearLayout headerLayout;
  MenuBuilder menu;
  private int id;
  NavigationMenuAdapter adapter;
  LayoutInflater layoutInflater;
  int subheaderTextAppearance=NO_TEXT_APPEARANCE_SET;
  ColorStateList subheaderColor;
  boolean textAppearanceActiveBoldEnabled=true;
  ColorStateList textColor;
  ColorStateList iconTintList;
  Drawable itemBackground;
  int itemHorizontalPadding;
  int itemVerticalPadding;
  int itemIconPadding;
  int itemIconSize;
  int dividerInsetStart;
  int dividerInsetEnd;
  int subheaderInsetStart;
  int subheaderInsetEnd;
  boolean hasCustomItemIconSize;
  boolean isBehindStatusBar=true;
  private int itemMaxLines;
  private int paddingTopDefault;
  int paddingSeparator;
  private int overScrollMode=-1;
  public void initForMenu(  Context context,  MenuBuilder menu){
    layoutInflater=LayoutInflater.from(context);
    this.menu=menu;
    //Resources res=context.getResources();
    //paddingSeparator=res.getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
  }
  public androidx.recyclerview.widget.RecyclerView getMenuView(  ViewGroup root){
    if (menuView == null) {
      menuView=(androidx.recyclerview.widget.RecyclerView)layoutInflater.inflate("@layout/design_navigation_menu_new",root,false);
      //menuView.//setAccessibilityDelegateCompat(new androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate(menuView));
      if (adapter == null) {
        adapter=new NavigationMenuAdapter();
        adapter.setHasStableIds(true);
      }
      if (overScrollMode != -1) {
        menuView.setOverScrollMode(overScrollMode);
      }
      //headerLayout=(LinearLayout)layoutInflater.inflate("@layout/design_navigation_item_header_new",menuView,false);
      //ViewCompat.setImportantForAccessibility(headerLayout,ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
      menuView.setAdapter(adapter);
    }
    return menuView;
  }
  public void updateMenuView(  boolean cleared){
    if (adapter != null) {
      adapter.update();
    }
  }
  public boolean flagActionItems(){
    return false;
  }
  public void setId(  int id){
    this.id=id;
  }
  public void addHeaderView(  View view){
    headerLayout.addView(view);
    menuView.setPadding(0,0,0,menuView.getPaddingBottom());
  }
  public void removeHeaderView(  View view){
    headerLayout.removeView(view);
    if (!hasHeader()) {
      menuView.setPadding(0,paddingTopDefault,0,menuView.getPaddingBottom());
    }
  }
  public int getHeaderCount(){
    return headerLayout.getChildCount();
  }
  private boolean hasHeader(){
    return getHeaderCount() > 0;
  }
  public View getHeaderView(  int index){
    return headerLayout.getChildAt(index);
  }
  public ColorStateList getItemTintList(){
    return iconTintList;
  }
  public void setItemIconTintList(  ColorStateList tint){
    iconTintList=tint;
    updateMenuView(false);
  }
  public ColorStateList getItemTextColor(){
    return textColor;
  }
  public void setItemTextColor(  ColorStateList textColor){
    this.textColor=textColor;
    updateMenuView(false);
  }
  public void setItemTextAppearance(  String resId){
    textAppearance=resId;
    updateMenuView(false);
  }
  public Drawable getItemBackground(){
    return itemBackground;
  }
  public void setItemBackground(  Drawable itemBackground){
    this.itemBackground=itemBackground;
    updateMenuView(false);
  }
  public int getItemHorizontalPadding(){
    return itemHorizontalPadding;
  }
  public void setItemHorizontalPadding(  int itemHorizontalPadding){
    this.itemHorizontalPadding=itemHorizontalPadding;
    updateMenuView(false);
  }
  public int getItemVerticalPadding(){
    return itemVerticalPadding;
  }
  public void setItemVerticalPadding(  int itemVerticalPadding){
    this.itemVerticalPadding=itemVerticalPadding;
    updateMenuView(false);
  }
  public void setDividerInsetStart(  int dividerInsetStart){
    this.dividerInsetStart=dividerInsetStart;
    updateMenuView(false);
  }
  public void setDividerInsetEnd(  int dividerInsetEnd){
    this.dividerInsetEnd=dividerInsetEnd;
    updateMenuView(false);
  }
  public void setSubheaderInsetStart(  int subheaderInsetStart){
    this.subheaderInsetStart=subheaderInsetStart;
    updateMenuView(false);
  }
  public void setSubheaderInsetEnd(  int subheaderInsetEnd){
    this.subheaderInsetEnd=subheaderInsetEnd;
    updateMenuView(false);
  }
  public int getItemIconPadding(){
    return itemIconPadding;
  }
  public void setItemIconPadding(  int itemIconPadding){
    this.itemIconPadding=itemIconPadding;
    updateMenuView(false);
  }
  public void setItemMaxLines(  int itemMaxLines){
    this.itemMaxLines=itemMaxLines;
    updateMenuView(false);
  }
  public int getItemMaxLines(){
    return itemMaxLines;
  }
  public void setItemIconSize(  int itemIconSize){
    if (this.itemIconSize != itemIconSize) {
      this.itemIconSize=itemIconSize;
      hasCustomItemIconSize=true;
      updateMenuView(false);
    }
  }
  public void setUpdateSuspended(  boolean updateSuspended){
    if (adapter != null) {
      adapter.setUpdateSuspended(updateSuspended);
    }
  }
private abstract static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(    View itemView){
      super(itemView);
    }
  }
private static class NormalViewHolder extends ViewHolder {
    public NormalViewHolder(    LayoutInflater inflater,    ViewGroup parent,    View.OnClickListener listener){
      super(inflater.inflate("@layout/design_navigation_item_new",parent,false));
      itemView.setMyAttribute("onClick",listener);inflater.recurseSet((ViewGroup) itemView, listener);
    }
  }
private static class SubheaderViewHolder extends ViewHolder {
    public SubheaderViewHolder(    LayoutInflater inflater,    ViewGroup parent){
      super(inflater.inflate("@layout/design_navigation_item_subheader_new",parent,false));
    }
  }
private static class SeparatorViewHolder extends ViewHolder {
    public SeparatorViewHolder(    LayoutInflater inflater,    ViewGroup parent){
      super(inflater.inflate("@layout/design_navigation_item_separator_new",parent,false));
    }
  }
private static class HeaderViewHolder extends ViewHolder {
    public HeaderViewHolder(    View itemView){
      super(itemView);
    }
  }
  final View.OnClickListener onClickListener=new View.OnClickListener(){
    public void onClick(    View view){
      NavigationMenuItemView itemView=(NavigationMenuItemView)getNavigationMenuItemView(view);
      setUpdateSuspended(true);
      MenuItemImpl item=itemView.getItemData();
      boolean result=menu.performItemAction(item,NavigationMenuPresenter.this,0);
      boolean checkStateChanged=false;
      if (item != null && item.isCheckable() && result) {
        adapter.setCheckedItem(item);
        checkStateChanged=true;
      }
      setUpdateSuspended(false);
      if (checkStateChanged) {
        updateMenuView(false);
      }
    }
  }
;
private class NavigationMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final int VIEW_TYPE_NORMAL=0;
    private static final int VIEW_TYPE_SUBHEADER=1;
    private static final int VIEW_TYPE_SEPARATOR=2;
    private static final int VIEW_TYPE_HEADER=3;
    private final ArrayList<NavigationMenuItem> items=new ArrayList<>();
    private MenuItemImpl checkedItem;
    private boolean updateSuspended;
    public int getItemCount(){
      return items.size();
    }
    public int getItemViewType(    int position){
      NavigationMenuItem item=items.get(position);
      if (item instanceof NavigationMenuSeparatorItem) {
        return VIEW_TYPE_SEPARATOR;
      }
 else       if (item instanceof NavigationMenuHeaderItem) {
        return VIEW_TYPE_HEADER;
      }
 else       if (item instanceof NavigationMenuTextItem) {
        NavigationMenuTextItem textItem=(NavigationMenuTextItem)item;
        if (textItem.getMenuItem().hasSubMenu()) {
          return VIEW_TYPE_SUBHEADER;
        }
 else {
          return VIEW_TYPE_NORMAL;
        }
      }
      throw new RuntimeException("Unknown item type.");
    }
    public ViewHolder onCreateViewHolder(    ViewGroup parent,    int viewType){
switch (viewType) {
case VIEW_TYPE_NORMAL:
        return new NormalViewHolder(layoutInflater,parent,onClickListener);
case VIEW_TYPE_SUBHEADER:
      return new SubheaderViewHolder(layoutInflater,parent);
case VIEW_TYPE_SEPARATOR:
    return new SeparatorViewHolder(layoutInflater,parent);
case VIEW_TYPE_HEADER: if (headerLayout == null) {headerLayout=(LinearLayout)layoutInflater.inflate("@layout/design_navigation_item_header_new",menuView,false);}((com.ashera.widget.ILifeCycleDecorator)parent).getWidget().getParent().invokeMethod("addHeaderView", headerLayout);
  return new HeaderViewHolder(headerLayout);
}
return null;
}
public void onBindViewHolder(ViewHolder holder,int position){
switch (getItemViewType(position)) {
case VIEW_TYPE_NORMAL:
{
  NavigationMenuItemView itemView=(NavigationMenuItemView)holder.itemView;
  itemView.setIconTintList(iconTintList);
  itemView.setTextAppearance(textAppearance);
  if (textColor != null) {
    itemView.setTextColor(textColor);
  }
  if (itemBackground != null) {itemView.setMyAttribute("background",itemBackground);}//ViewCompat.setBackground(itemView,itemBackground != null ? itemBackground.getConstantState().newDrawable() : null);
  if (false) {
    //itemView.setForeground(itemForeground.getConstantState().newDrawable());
  }
  NavigationMenuTextItem item=(NavigationMenuTextItem)items.get(position);
  itemView.setNeedsEmptyIcon(item.needsEmptyIcon);
  itemView.setPadding(itemHorizontalPadding,itemVerticalPadding,itemHorizontalPadding,itemVerticalPadding);
  itemView.setIconPadding(itemIconPadding);
  if (hasCustomItemIconSize) {
    itemView.setIconSize(itemIconSize);
  }
  itemView.setMaxLines(itemMaxLines);
  itemView.initialize(item.getMenuItem(),textAppearanceActiveBoldEnabled);
  //setAccessibilityDelegate(itemView,position,false);
  break;
}
case VIEW_TYPE_SUBHEADER:
{
TextView subHeader=(TextView)holder.itemView;
NavigationMenuTextItem item=(NavigationMenuTextItem)items.get(position);
subHeader.setText(item.getMenuItem().getTitle());
//TextViewCompat.setTextAppearance(subHeader,subheaderTextAppearance);
subHeader.setPadding(subheaderInsetStart,subHeader.getPaddingTop(),subheaderInsetEnd,subHeader.getPaddingBottom());
if (subheaderColor != null) {
  subHeader.setTextColor(subheaderColor);
}
//setAccessibilityDelegate(subHeader,position,true);
break;
}
case VIEW_TYPE_SEPARATOR:
{
NavigationMenuSeparatorItem item=(NavigationMenuSeparatorItem)items.get(position);
holder.itemView.setPadding(dividerInsetStart,item.getPaddingTop(),dividerInsetEnd,item.getPaddingBottom());
break;
}
}
}
public void onViewRecycled(ViewHolder holder){
if (holder instanceof NormalViewHolder) {
((NavigationMenuItemView)holder.itemView).recycle();
}
}
public void update(){
prepareMenuItems();
notifyDataSetChanged();
}
private void prepareMenuItems(){
if (updateSuspended) {
return;
}
updateSuspended=true;
items.clear();
items.add(new NavigationMenuHeaderItem());
int currentGroupId=-1;
int currentGroupStart=0;
boolean currentGroupHasIcon=false;
for (int i=0, totalSize=menu.getVisibleItems().size(); i < totalSize; i++) {
MenuItemImpl item=menu.getVisibleItems().get(i);
if (item.isChecked()) {
setCheckedItem(item);
}
if (item.isCheckable()) {
item.setExclusiveCheckable(false);
}
if (item.hasSubMenu()) {
androidx.appcompat.view.menu.SubMenuBuilder subMenu=item.getSubMenu();
if (subMenu.hasVisibleItems()) {
if (i != 0) {
items.add(new NavigationMenuSeparatorItem(paddingSeparator,0));
}
items.add(new NavigationMenuTextItem(item));
boolean subMenuHasIcon=false;
int subMenuStart=items.size();
for (int j=0, size=subMenu.size(); j < size; j++) {
MenuItemImpl subMenuItem=(MenuItemImpl)subMenu.getItem(j);
if (subMenuItem.isVisible()) {
  if (!subMenuHasIcon && subMenuItem.getIcon() != null) {
    subMenuHasIcon=true;
  }
  if (subMenuItem.isCheckable()) {
    subMenuItem.setExclusiveCheckable(false);
  }
  if (item.isChecked()) {
    setCheckedItem(item);
  }
  items.add(new NavigationMenuTextItem(subMenuItem));
}
}
if (subMenuHasIcon) {
appendTransparentIconIfMissing(subMenuStart,items.size());
}
}
}
 else {
int groupId=item.getGroupId();
if (groupId != currentGroupId) {
currentGroupStart=items.size();
currentGroupHasIcon=item.getIcon() != null;
if (i != 0) {
currentGroupStart++;
items.add(new NavigationMenuSeparatorItem(paddingSeparator,paddingSeparator));
}
}
 else if (!currentGroupHasIcon && item.getIcon() != null) {
currentGroupHasIcon=true;
appendTransparentIconIfMissing(currentGroupStart,items.size());
}
NavigationMenuTextItem textItem=new NavigationMenuTextItem(item);
textItem.needsEmptyIcon=currentGroupHasIcon;
items.add(textItem);
currentGroupId=groupId;
}
}
updateSuspended=false;
}
private void appendTransparentIconIfMissing(int startIndex,int endIndex){
for (int i=startIndex; i < endIndex; i++) {
NavigationMenuTextItem textItem=(NavigationMenuTextItem)items.get(i);
textItem.needsEmptyIcon=true;
}
}
public void setCheckedItem(MenuItemImpl checkedItem){
if (this.checkedItem == checkedItem || !checkedItem.isCheckable()) {
return;
}
if (this.checkedItem != null) {
this.checkedItem.setChecked(false);
}
this.checkedItem=checkedItem;
checkedItem.setChecked(true);
}
public void setUpdateSuspended(boolean updateSuspended){
this.updateSuspended=updateSuspended;
}
}
private interface NavigationMenuItem {
}
private static class NavigationMenuTextItem implements NavigationMenuItem {
private final MenuItemImpl menuItem;
boolean needsEmptyIcon;
NavigationMenuTextItem(MenuItemImpl item){
menuItem=item;
}
public MenuItemImpl getMenuItem(){
return menuItem;
}
}
private static class NavigationMenuSeparatorItem implements NavigationMenuItem {
private final int paddingTop;
private final int paddingBottom;
public NavigationMenuSeparatorItem(int paddingTop,int paddingBottom){
this.paddingTop=paddingTop;
this.paddingBottom=paddingBottom;
}
public int getPaddingTop(){
return paddingTop;
}
public int getPaddingBottom(){
return paddingBottom;
}
}
private static class NavigationMenuHeaderItem implements NavigationMenuItem {
NavigationMenuHeaderItem(){
}
}
public static class LayoutInflater {
public static LayoutInflater from(Context context){
return new LayoutInflater();
}
public View inflate(String layout,ViewGroup parent,boolean b){
return parent.inflateView(layout);
}
public void recurseSet(ViewGroup parent,r.android.view.View.OnClickListener onClickListener){
if (com.ashera.widget.PluginInvoker.getOS().equalsIgnoreCase("swt")) {
for (int i=0; i < parent.getChildCount(); i++) {
View child=parent.getChildAt(i);
child.setMyAttribute("onClick",onClickListener);
if (child instanceof ViewGroup) {
recurseSet((ViewGroup)child,onClickListener);
}
}
}
}
}
private String textAppearance;
private View getNavigationMenuItemView(View v){
while (!(v instanceof NavigationMenuItemView)) {
v=(View)v.getParent();
}
return v;
}
}
