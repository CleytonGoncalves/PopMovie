// Generated code from Butter Knife. Do not modify!
package com.cleytongoncalves.popmovies.ui.detailmovie;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DetailFragment$$ViewBinder<T extends com.cleytongoncalves.popmovies.ui.detailmovie.DetailFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492978, "field 'name'");
    target.name = finder.castView(view, 2131492978, "field 'name'");
    view = finder.findRequiredView(source, 2131492980, "field 'releaseYear'");
    target.releaseYear = finder.castView(view, 2131492980, "field 'releaseYear'");
    view = finder.findRequiredView(source, 2131492981, "field 'rating'");
    target.rating = finder.castView(view, 2131492981, "field 'rating'");
    view = finder.findRequiredView(source, 2131492982, "field 'overview'");
    target.overview = finder.castView(view, 2131492982, "field 'overview'");
    view = finder.findRequiredView(source, 2131492979, "field 'poster'");
    target.poster = finder.castView(view, 2131492979, "field 'poster'");
  }

  @Override public void unbind(T target) {
    target.name = null;
    target.releaseYear = null;
    target.rating = null;
    target.overview = null;
    target.poster = null;
  }
}
