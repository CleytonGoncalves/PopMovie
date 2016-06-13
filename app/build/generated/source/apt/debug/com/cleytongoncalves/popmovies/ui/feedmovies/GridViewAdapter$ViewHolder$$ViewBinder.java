// Generated code from Butter Knife. Do not modify!
package com.cleytongoncalves.popmovies.ui.feedmovies;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GridViewAdapter$ViewHolder$$ViewBinder<T extends com.cleytongoncalves.popmovies.ui.feedmovies.GridViewAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492984, "field 'posterView'");
    target.posterView = finder.castView(view, 2131492984, "field 'posterView'");
    view = finder.findRequiredView(source, 2131492985, "field 'name'");
    target.name = finder.castView(view, 2131492985, "field 'name'");
  }

  @Override public void unbind(T target) {
    target.posterView = null;
    target.name = null;
  }
}
