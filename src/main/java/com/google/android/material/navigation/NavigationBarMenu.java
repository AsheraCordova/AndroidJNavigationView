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
 * Copyright (C) 2020 The Android Open Source Project
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
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import r.android.view.MenuItem;
public final class NavigationBarMenu extends MenuBuilder {
  private final Class<?> viewClass;
  private final int maxItemCount;
  public NavigationBarMenu(     Class<?> viewClass,  int maxItemCount){
    //super(context);
    this.viewClass=viewClass;
    this.maxItemCount=maxItemCount;
  }
  public int getMaxItemCount(){
    return maxItemCount;
  }
  protected MenuItem addInternal(  int group,  int id,  int categoryOrder,  CharSequence title){
    if (size() + 1 > maxItemCount) {
      String viewClassName=viewClass.getSimpleName();
      throw new IllegalArgumentException("Maximum number of items supported by " + viewClassName + " is "+ maxItemCount+ ". Limit can be checked with "+ viewClassName+ "#getMaxItemCount()");
    }
    stopDispatchingItemsChanged();
    final MenuItem item=super.addInternal(group,id,categoryOrder,title);
    if (item instanceof MenuItemImpl) {
      ((MenuItemImpl)item).setExclusiveCheckable(true);
    }
    startDispatchingItemsChanged();
    return item;
  }
}
