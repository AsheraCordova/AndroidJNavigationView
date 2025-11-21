//start - license
/*
 * Copyright (c) 2025 Ashera Cordova
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
//end - license
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.material.bottomnavigation;
import r.android.content.Context;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;
public class BottomNavigationView extends NavigationBarView {
  static final int MAX_ITEM_COUNT=5;
  public void setItemHorizontalTranslationEnabled(  boolean itemHorizontalTranslationEnabled){
    BottomNavigationMenuView menuView=(BottomNavigationMenuView)getMenuView();
    if (menuView.isItemHorizontalTranslationEnabled() != itemHorizontalTranslationEnabled) {
      menuView.setItemHorizontalTranslationEnabled(itemHorizontalTranslationEnabled);
      getPresenter().updateMenuView(false);
    }
  }
  public boolean isItemHorizontalTranslationEnabled(){
    return ((BottomNavigationMenuView)getMenuView()).isItemHorizontalTranslationEnabled();
  }
  public int getMaxItemCount(){
    return MAX_ITEM_COUNT;
  }
  protected NavigationBarMenuView createNavigationBarMenuView(  Context context){
    String name = BottomNavigationMenuView.class.getName();com.ashera.widget.IWidget widget= com.ashera.widget.WidgetFactory.createWidget(name, name, (com.ashera.widget.HasWidgets) ((com.ashera.widget.ILifeCycleDecorator) this).getWidget(), false);return (BottomNavigationMenuView) widget.asWidget();//new BottomNavigationMenuView();;
  }
}
