// Generated code from Butter Knife. Do not modify!
package com.cleytongoncalves.popmovies.ui.feedmovies;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GridFragment$$ViewBinder<T extends com.cleytongoncalves.popmovies.ui.feedmovies.GridFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492983, "field 'gridView' and method 'onItemClick'");
    target.gridView = finder.castView(view, 2131492983, "field 'gridView'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemClick(p2);
        }
      });
  }

  @Override public void unbind(T target) {
    target.gridView = null;
  }
}
