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
package com.google.android.material.navigation;
import r.android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
public class NavigationBarPresenter implements MenuPresenter {
  private MenuBuilder menu;
  private NavigationBarMenuView menuView;
  private boolean updateSuspended=false;
  private int id;
  public void setMenuView(  NavigationBarMenuView menuView){
    this.menuView=menuView;
  }
  public void initForMenu(  Context context,  MenuBuilder menu){
    this.menu=menu;
    menuView.initialize(this.menu);
  }
  public void updateMenuView(  boolean cleared){
    if (updateSuspended) {
      return;
    }
    if (cleared) {
      menuView.buildMenuView();
    }
 else {
      menuView.updateMenuView();
    }
  }
  public boolean flagActionItems(){
    return false;
  }
  public void setId(  int id){
    this.id=id;
  }
  public void setUpdateSuspended(  boolean updateSuspended){
    this.updateSuspended=updateSuspended;
  }
}
