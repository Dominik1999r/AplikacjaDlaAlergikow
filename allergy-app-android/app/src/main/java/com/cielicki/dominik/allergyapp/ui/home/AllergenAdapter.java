package com.cielicki.dominik.allergyapp.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cielicki.dominik.allergyapp.R;
import com.cielicki.dominik.allergyapp.common.Utils;
import com.cielicki.dominik.allergyapprestapi.db.Allergen;
import com.cielicki.dominik.allergyapprestapi.db.VoivodeshipAllergen;
import com.cielicki.dominik.allergyapprestapi.db.model.VoivodeshipAllergenList;

/**
 * Adapter dla RecyclerView wyświetlającego listę alergenów w województwie.
 */
public class AllergenAdapter extends RecyclerView.Adapter<AllergenAdapter.ViewHolder> {

    private VoivodeshipAllergenList voivodeshipAllergenList;
    private HomeViewModel homeViewModel;

    AllergenAdapter(VoivodeshipAllergenList voivodeshipAllergenList, HomeViewModel homeViewModel) {
        this.voivodeshipAllergenList = voivodeshipAllergenList;
        this.homeViewModel = homeViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View medicineView = layoutInflater.inflate(R.layout.allergen_row, parent, false);

        return new ViewHolder(context, medicineView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoivodeshipAllergen voivodeshipAllergen = voivodeshipAllergenList.getVoivodeshipAllergenList().get(position);

        holder.allergenNameTextView.setText(voivodeshipAllergen.getId().getAllergen().getName());
        holder.allergenValueTextView.setText(Utils.formatDate(voivodeshipAllergen.getId().getStartDate()) + " - " + Utils.formatDate(voivodeshipAllergen.getId().getEndDate()));

        Button button = holder.allergenIndicatorButton;
        Allergen allergen = voivodeshipAllergen.getId().getAllergen();
        double base = allergen.getMaxValue().doubleValue() - allergen.getMinValue().doubleValue();
        double concentration = (voivodeshipAllergen.getValue().doubleValue() - allergen.getMinValue().doubleValue()) / base;
        concentration = concentration < 0 ? 0 : concentration;
        concentration = concentration > 1 ? concentration % 1 : concentration;

        int color = getBlendedColor(100 - (int) (concentration * 100));
        button.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return voivodeshipAllergenList.getVoivodeshipAllergenList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView allergenNameTextView;
        public TextView allergenValueTextView;
        public Button allergenIndicatorButton;
        public Context context;

        public ViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);

            allergenNameTextView = (TextView) itemView.findViewById(R.id.allergen_name);
            allergenValueTextView = (TextView) itemView.findViewById(R.id.allergen_value);
            allergenIndicatorButton = (Button) itemView.findViewById(R.id.allergen_indicator);
            this.context = context;
        }
    }

    /**
     * Tworzy kolor między zielonym, a czerwonym na podstawie procentu.
     *
     * @param percentage Procent.
     * @return Kolor między zielonym, a czerownym.
     */
    public int getBlendedColor(int percentage)
    {
        if (percentage < 50) {
            return interpolate(Color.RED, Color.YELLOW, percentage / 50.0);

        } else {

            return interpolate(Color.YELLOW, Color.rgb(158,253,56), (percentage - 50) / 50.0);
        }
    }

    /**
     * Przekształca kolor na podstawie ułamka.
     *
     * @param color1 Pierwszy kolor.
     * @param color2 Drugi kolor.
     * @param fraction Ułamek.
     * @return Kolor pomiędzy color1 i color2.
     */
    private int interpolate(int color1, int color2, double fraction)
    {
        int r = interpolateBasicColor(Color.red(color1), Color.red(color2), fraction);
        int g = interpolateBasicColor(Color.green(color1), Color.green(color2), fraction);
        int b = interpolateBasicColor(Color.blue(color1), Color.blue(color2), fraction);
        return Color.rgb((int)r, (int)g, (int)b);
    }

    /**
     * Przeształca kolor podstwaowoy na podstawie ułamka.
     *
     * @param color1 Pierwszy kolor.
     * @param color2 Drugi kolor.
     * @param fraction ułamek.
     * @return Kolor pomiędzy color1 i color2.
     */
    private int interpolateBasicColor(int color1, int color2, double fraction)
    {
        return (int) (color1 + (color2 - color1) * fraction);
    }
}
