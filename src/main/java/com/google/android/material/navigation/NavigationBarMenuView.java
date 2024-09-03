package com.google.android.material.navigation;
import r.android.content.Context;
import r.android.content.res.ColorStateList;
import r.android.graphics.drawable.Drawable;
import r.android.util.Pools;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import r.android.util.SparseArray;
import r.android.view.Menu;
import r.android.view.MenuItem;
import r.android.view.View;
import r.android.view.ViewGroup;
import com.google.android.material.badge.BadgeDrawable;
public abstract class NavigationBarMenuView extends ViewGroup {
  private static final int ITEM_POOL_SIZE=5;
  private static final int[] CHECKED_STATE_SET={r.android.R.attr.state_checked};
  private static final int[] DISABLED_STATE_SET={-r.android.R.attr.state_enabled};
  private final OnClickListener onClickListener;
  private final Pools.Pool<NavigationBarItemView> itemPool=new Pools.SynchronizedPool<>(ITEM_POOL_SIZE);
  private final SparseArray<OnTouchListener> onTouchListeners=new SparseArray<>(ITEM_POOL_SIZE);
  private int labelVisibilityMode;
  private NavigationBarItemView[] buttons;
  private int selectedItemId=0;
  private int selectedItemPosition=0;
  private ColorStateList itemIconTint;
  private int itemIconSize;
  private ColorStateList itemTextColorFromUser;
  private final ColorStateList itemTextColorDefault;
  private String itemTextAppearanceInactive;
  private String itemTextAppearanceActive;
  private Drawable itemBackground;
  private int itemBackgroundRes;
  private SparseArray<BadgeDrawable> badgeDrawables=new SparseArray<>(ITEM_POOL_SIZE);
  private NavigationBarPresenter presenter;
  private MenuBuilder menu;
  public NavigationBarMenuView(){
     
    itemTextColorDefault=createDefaultColorStateList(0);//createDefaultColorStateList(r.android.R.attr.textColorSecondary);
    //set=new AutoTransition();
    //set.setOrdering(TransitionSet.ORDERING_TOGETHER);
    //set.setDuration(ACTIVE_ANIMATION_DURATION_MS);
    //set.setInterpolator(new FastOutSlowInInterpolator());
    //set.addTransition(new TextScale());
    onClickListener=new OnClickListener(){
      public void onClick(      View v){v=getNavigationBarItemView(v);
        final NavigationBarItemView itemView=(NavigationBarItemView)v;
        MenuItem item=itemView.getItemData();
        if (!menu.performItemAction(item,presenter,0)) {
          item.setChecked(true);
        }
      }
    }
;
    //ViewCompat.setImportantForAccessibility(this,ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
  }
  public void initialize(  MenuBuilder menu){
    this.menu=menu;
  }
  public void setIconTintList(  ColorStateList tint){
    itemIconTint=tint;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setIconTintList(tint);
      }
    }
  }
  public void setItemIconSize(  int iconSize){
    this.itemIconSize=iconSize;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setIconSize(iconSize);
      }
    }
  }
  public void setItemTextColor(  ColorStateList color){
    itemTextColorFromUser=color;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setTextColor(color);
      }
    }
  }
  public void setItemTextAppearanceInactive(  String textAppearanceRes){
    this.itemTextAppearanceInactive=textAppearanceRes;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setTextAppearanceInactive(textAppearanceRes);
        if (itemTextColorFromUser != null) {
          item.setTextColor(itemTextColorFromUser);
        }
      }
    }
  }
  public void setItemTextAppearanceActive(  String textAppearanceRes){
    this.itemTextAppearanceActive=textAppearanceRes;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setTextAppearanceActive(textAppearanceRes);
        if (itemTextColorFromUser != null) {
          item.setTextColor(itemTextColorFromUser);
        }
      }
    }
  }
  public void setItemBackground(  Drawable background){
    itemBackground=background;
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        item.setItemBackground(background);
      }
    }
  }
  public void setLabelVisibilityMode(  int labelVisibilityMode){
    this.labelVisibilityMode=labelVisibilityMode;
  }
  public int getLabelVisibilityMode(){
    return labelVisibilityMode;
  }
  public void setPresenter(  NavigationBarPresenter presenter){
    this.presenter=presenter;
  }
  public void buildMenuView(){
    removeAllViews();
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        if (item != null) {
          itemPool.release(item);
          //item.removeBadge();
        }
      }
    }
    if (menu.size() == 0) {
      selectedItemId=0;
      selectedItemPosition=0;
      buttons=null;
      return;
    }
    //removeUnusedBadges();
    buttons=new NavigationBarItemView[menu.size()];
    boolean shifting=isShifting(labelVisibilityMode,menu.getVisibleItems().size());
    for (int i=0; i < menu.size(); i++) {
      presenter.setUpdateSuspended(true);
      menu.getItem(i).setCheckable(true);
      presenter.setUpdateSuspended(false);
      NavigationBarItemView child=getNewItem();
      buttons[i]=child;
      child.setIconTintList(itemIconTint);
      child.setIconSize(itemIconSize);
      child.setTextColor(itemTextColorDefault);
      child.setTextAppearanceInactive(itemTextAppearanceInactive);
      child.setTextAppearanceActive(itemTextAppearanceActive);
      child.setTextColor(itemTextColorFromUser);
      if (itemBackground != null) {
        child.setItemBackground(itemBackground);
      }
 else {
        //child.setItemBackground(itemBackgroundRes);
      }
      child.setShifting(shifting);
      child.setLabelVisibilityMode(labelVisibilityMode);
      MenuItemImpl item=(MenuItemImpl)menu.getItem(i);
      child.initialize(item,0);
      child.setItemPosition(i);
      int itemId=item.getItemId();
      //child.setOnTouchListener(onTouchListeners.get(itemId));
      child.setMyAttribute("onClick",onClickListener);recurseSet(child, onClickListener);
      if (selectedItemId != Menu.NONE && itemId == selectedItemId) {
        selectedItemPosition=i;
      }
      //setBadgeIfNeeded(child);
       if (!hasChild(child)) {addView(child);}
    }
    selectedItemPosition=Math.min(menu.size() - 1,selectedItemPosition);
    menu.getItem(selectedItemPosition).setChecked(true);
  }
  public void updateMenuView(){
    if (menu == null || buttons == null) {
      return;
    }
    final int menuSize=menu.size();
    if (menuSize != buttons.length) {
      buildMenuView();
      return;
    }
    int previousSelectedId=selectedItemId;
    for (int i=0; i < menuSize; i++) {
      MenuItem item=menu.getItem(i);
      if (item.isChecked()) {
        selectedItemId=item.getItemId();
        selectedItemPosition=i;
      }
    }
    if (previousSelectedId != selectedItemId) {
      //TransitionManager.beginDelayedTransition(this,set);
    }
    boolean shifting=isShifting(labelVisibilityMode,menu.getVisibleItems().size());
    for (int i=0; i < menuSize; i++) {
      presenter.setUpdateSuspended(true);
      buttons[i].setLabelVisibilityMode(labelVisibilityMode);
      buttons[i].setShifting(shifting);
      buttons[i].initialize((MenuItemImpl)menu.getItem(i),0);
      presenter.setUpdateSuspended(false);
    }
  }
  private NavigationBarItemView getNewItem(){
    NavigationBarItemView item=itemPool.acquire();
    if (item == null) {
      item=createNavigationBarItemView(getContext());
    }
    return item;
  }
  public int getSelectedItemId(){
    return selectedItemId;
  }
  protected boolean isShifting(  int labelVisibilityMode,  int childCount){
    return labelVisibilityMode == NavigationBarView.LABEL_VISIBILITY_AUTO ? childCount > 3 : labelVisibilityMode == NavigationBarView.LABEL_VISIBILITY_SELECTED;
  }
  BadgeDrawable getOrCreateBadge(  int menuItemId){
    validateMenuItemId(menuItemId);
    BadgeDrawable badgeDrawable=badgeDrawables.get(menuItemId);
    if (badgeDrawable == null) {
      badgeDrawable=BadgeDrawable.create(getContext());
      badgeDrawables.put(menuItemId,badgeDrawable);
    }
    NavigationBarItemView itemView=findItemView(menuItemId);
    if (itemView != null) {
      itemView.setBadge(badgeDrawable);
    }
    return badgeDrawable;
  }
  public NavigationBarItemView findItemView(  int menuItemId){
    validateMenuItemId(menuItemId);
    if (buttons != null) {
      for (      NavigationBarItemView itemView : buttons) {
        if (itemView.getId() == menuItemId) {
          return itemView;
        }
      }
    }
    return null;
  }
  protected abstract NavigationBarItemView createNavigationBarItemView(  Context context);
  protected int getSelectedItemPosition(){
    return selectedItemPosition;
  }
  protected MenuBuilder getMenu(){
    return menu;
  }
  private boolean isValidId(  int viewId){
    return viewId != View.NO_ID;
  }
  private void validateMenuItemId(  int viewId){
    if (!isValidId(viewId)) {
      throw new IllegalArgumentException(viewId + " is not a valid view id");
    }
  }
  private void recurseSet(  ViewGroup parent,  OnClickListener onClickListener){
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
  private View getNavigationBarItemView(  View v){
    while (!(v instanceof NavigationBarItemView)) {
      v=(View)v.getParent();
    }
    return v;
  }
  protected static final int[] EMPTY_STATE_SET=r.android.util.StateSet.get(0);
  public ColorStateList createDefaultColorStateList(  int baseColorThemeAttr){
    return new ColorStateList(new int[][]{DISABLED_STATE_SET,CHECKED_STATE_SET,EMPTY_STATE_SET},new int[]{r.android.graphics.Color.WHITE,r.android.graphics.Color.WHITE,r.android.graphics.Color.BLACK});
  }
  @Override public void requestLayout(){
    super.requestLayout();
    if (buttons != null) {
      for (      NavigationBarItemView item : buttons) {
        if (item != null) {
          item.requestLayout();
        }
      }
    }
  }
}
