package net.apetheory.publicise.server.api.documentation.reader;

import net.apetheory.publicise.server.api.documentation.model.ParameterModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

/**
 * Created by Christoph on 02.05.2015.
 */
public class BeanParameterReader implements Reader<Void> {

    private OnReadListener<ParameterModel> onReadListener;
    private Class<?> beanParam;

    public BeanParameterReader(Parameter parameter) {
        this.beanParam = parameter.getType();
    }

    @Override
    public Void read() {
        for (Constructor constructor : beanParam.getConstructors()) {
            for (Parameter parameter : constructor.getParameters()) {
                ParameterReader parameterReader = new ParameterReader(parameter);
                ParameterModel model = parameterReader.read();

                if (onReadListener != null) {
                    onReadListener.onRead(model);
                }
            }
        }
        return null;
    }

    public void setOnReadListener(OnReadListener<ParameterModel> listener) {
        this.onReadListener = listener;
    }

    public interface OnReadListener<TModel> {
        void onRead(TModel model);
    }
}
