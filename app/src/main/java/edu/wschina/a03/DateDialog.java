package edu.wschina.a03;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import edu.wschina.a03.databinding.DialogDateBinding;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DateDialog extends DialogFragment {
    private static final int INTERVAL = 9;
    private DialogDateBinding binding;
    private LocalDate current = LocalDate.now().withDayOfMonth(1).minusMonths(4);
    private LocalDate firstDayDate;
    private int squareWidth = 0;
    private boolean first = true;
    private final List<DayOfWeek> weeks = Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY);
    private DateRange currentDateRange;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogDateBinding.inflate(getLayoutInflater());
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setView(binding.getRoot())
                .setCancelable(true)
                .create();

        binding.apply.setOnClickListener(v -> dialog.cancel());

        binding.monthLast.setOnClickListener(v -> {
            current = current.minusMonths(1);
            refreshDateLayout();
            if (currentDateRange != null && currentDateRange.current.equals(this.current)) {
                refreshSelector(currentDateRange.firstIndex, currentDateRange.endIndex);
            }
        });

        binding.monthNext.setOnClickListener(v -> {
            current = current.plusMonths(1);
            refreshDateLayout();
            if (currentDateRange != null && currentDateRange.current.equals(this.current)) {
                refreshSelector(currentDateRange.firstIndex, currentDateRange.endIndex);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getDecorView().setPadding(32, 0, 32, 0);

        binding.dateLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            this.squareWidth = binding.dateLayout.getWidth() / 7;
            if (this.first) {
                this.first = false;
                refreshDateLayout();
            }
        });

        final int[] firstIndex = {-1};
        final int[] endIndex = {-1};

        binding.dateLayout.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    firstIndex[0] = calcIndex(event.getX(), event.getY());
                    if (firstIndex[0] < 7) return true;
                    clearSelector();
                    refreshSelector(firstIndex[0], firstIndex[0]);
                    refreshFromAndTo(firstIndex[0], firstIndex[0]);
                    break;
                case MotionEvent.ACTION_UP:
                    endIndex[0] = calcIndex(event.getX(), event.getY());
                    if (endIndex[0] < 7) return true;
                    int realFirstIndex = Math.min(firstIndex[0], endIndex[0]);
                    int realEndIndex = Math.max(firstIndex[0], endIndex[0]);
                    refreshSelector(realFirstIndex, realEndIndex);
                    refreshFromAndTo(realFirstIndex, realEndIndex);
                    break;
                case MotionEvent.ACTION_MOVE:
                    endIndex[0] = calcIndex(event.getX(), event.getY());
                    if (endIndex[0] < 7) return true;
                    realFirstIndex = Math.min(firstIndex[0], endIndex[0]);
                    realEndIndex = Math.max(firstIndex[0], endIndex[0]);
                    refreshSelector(realFirstIndex, realEndIndex);
                    refreshFromAndTo(realFirstIndex, realEndIndex);
                    break;
            }
            return true;
        });

        return dialog;
    }

    private void refreshDateLayout() {
        binding.current.setText(current.format(DateTimeFormatter.ofPattern("MMMM, yyyy", Locale.ENGLISH)));
        binding.dateLayout.removeAllViews();

        for (int i = 0; i < weeks.size(); i++) {
            DayOfWeek dayOfWeek = weeks.get(i);
            String name = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            TextView weekView = new TextView(requireContext());
            weekView.setText(name);
            weekView.setTextSize(16f);
            weekView.setGravity(Gravity.CENTER);
            weekView.setTextColor(requireContext().getColor(R.color.primary));
            weekView.setWidth(this.squareWidth);
            weekView.setHeight(this.squareWidth);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.columnSpec = GridLayout.spec(i % 7);
            params.rowSpec = GridLayout.spec(0);
            weekView.setLayoutParams(params);

            binding.dateLayout.addView(weekView);
        }

        this.firstDayDate = current.minusDays(current.getDayOfWeek().getValue() % 7);
        LocalDate date = firstDayDate;
        int total = (current.getDayOfWeek().getValue() % 7) + current.lengthOfMonth();
        total += (7 - total % 7) % 7;

        for (int i = 0; i < total; i++) {
            TextView dayView = new TextView(requireContext());
            dayView.setText(String.valueOf(date.getDayOfMonth()));
            dayView.setTextSize(16f);
            dayView.setGravity(Gravity.CENTER);
            dayView.setTextColor(requireContext().getColor(
                    date.getMonthValue() == current.getMonthValue() ? R.color.black : R.color.gray
            ));

            FrameLayout.LayoutParams dayParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            dayView.setLayoutParams(dayParams);

            FrameLayout dayLayout = new FrameLayout(requireContext());
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.width = squareWidth;
            layoutParams.height = squareWidth;
            layoutParams.setMargins(0, INTERVAL, 0, INTERVAL);
            layoutParams.columnSpec = GridLayout.spec(i % 7);
            layoutParams.rowSpec = GridLayout.spec(i / 7 + 1);
            dayLayout.setLayoutParams(layoutParams);
            dayLayout.setPadding(9, 0, 0, 0);

            dayLayout.addView(dayView);
            binding.dateLayout.addView(dayLayout);

            date = date.plusDays(1);
        }
    }

    private void clearSelector() {
        for (int i = 0; i < binding.dateLayout.getChildCount(); i++) {
            binding.dateLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void refreshSelector(int firstIndex, int endIndex) {
        if (firstIndex < 7) return;

        for (int i = 0; i < binding.dateLayout.getChildCount(); i++) {
            View layout = binding.dateLayout.getChildAt(i);
            if (i < 7) continue;

            if (i == firstIndex) {
                layout.setBackgroundResource(i % 7 == 6 || firstIndex == endIndex ? R.drawable.bg_day_circle : R.drawable.bg_day_start);
            } else if (i == endIndex) {
                layout.setBackgroundResource(i % 7 == 0 ? R.drawable.bg_day_circle : R.drawable.bg_day_end);
            } else if (i >= firstIndex && i <= endIndex) {
                if (i % 7 == 0) {
                    layout.setBackgroundResource(R.drawable.bg_day_start);
                } else if (i % 7 == 6) {
                    layout.setBackgroundResource(R.drawable.bg_day_end);
                } else {
                    layout.setBackgroundColor(requireContext().getColor(R.color.secondary));
                }
            } else {
                layout.setBackground(null);
            }

            TextView textView = (TextView) ((FrameLayout) layout).getChildAt(0);
            if (i == firstIndex || i == endIndex) {
                textView.setBackgroundResource(R.drawable.fg_day_circle);
                textView.setTextColor(Color.WHITE);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                textView.setBackground(null);
                LocalDate date = firstDayDate.plusDays(i - 7);
                if (date.getMonthValue() == current.getMonthValue()) {
                    textView.setTextColor(Color.BLACK);
                } else {
                    textView.setTextColor(requireContext().getColor(R.color.gray));
                }
                textView.setTypeface(Typeface.DEFAULT);
            }
        }
    }

    private int calcIndex(float x, float y) {
        int index = (int) (y / (squareWidth + INTERVAL * 2)) * 7 + (int) (x / squareWidth);
        if (index < 0) return 0;
        if (index >= binding.dateLayout.getChildCount())
            return binding.dateLayout.getChildCount() - 1;
        return index;
    }

    private static class DateRange {
        LocalDate current;
        int firstIndex;
        int endIndex;

        DateRange(LocalDate current, int firstIndex, int endIndex) {
            this.current = current;
            this.firstIndex = firstIndex;
            this.endIndex = endIndex;
        }
    }

    private void refreshFromAndTo(int firstIndex, int endIndex) {
        LocalDate firstDate = this.firstDayDate.plusDays(firstIndex - 7);
        LocalDate endDate = this.firstDayDate.plusDays(endIndex - 7);
        binding.from.setText(firstDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.ENGLISH)));
        binding.to.setText(endDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.ENGLISH)));
        this.currentDateRange = new DateRange(this.current, firstIndex, endIndex);
    }
}
