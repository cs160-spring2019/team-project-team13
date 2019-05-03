// Generated code from Butter Knife. Do not modify!
package com.example.petplant.reminders;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class CreateEditReminder$$ViewBinder<T extends CreateEditReminder> implements ViewBinder<T> {
  @Override
  public Unbinder bind(Finder finder, T target, Object source) {
    return new InnerUnbinder<>(target, finder, source);
  }

  protected static class InnerUnbinder<T extends CreateEditReminder> implements Unbinder {
    protected T target;

    private View view2131230991;

    private View view2131230799;

    protected InnerUnbinder(final T target, Finder finder, Object source) {
      this.target = target;

      View view;
      target.timeText = finder.findRequiredViewAsType(source, 2131230989, "field 'timeText'", TextView.class);
      target.dateText = finder.findRequiredViewAsType(source, 2131230798, "field 'dateText'", TextView.class);
      view = finder.findRequiredView(source, 2131230991, "method 'timePicker'");
      view2131230991 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.timePicker();
        }
      });
      view = finder.findRequiredView(source, 2131230799, "method 'datePicker'");
      view2131230799 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.datePicker(p0);
        }
      });
    }

    @Override
    public void unbind() {
      T target = this.target;
      if (target == null) throw new IllegalStateException("Bindings already cleared.");

      target.timeText = null;
      target.dateText = null;

      view2131230991.setOnClickListener(null);
      view2131230991 = null;
      view2131230799.setOnClickListener(null);
      view2131230799 = null;

      this.target = null;
    }
  }
}
