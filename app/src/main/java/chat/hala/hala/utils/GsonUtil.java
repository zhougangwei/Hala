package chat.hala.hala.utils;

import android.text.TextUtils;

import com.blankj.utilcode.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class GsonUtil {





   public static Gson getGson(){
       Gson gson =  new GsonBuilder()
               .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
               .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
               .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
               .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
               .registerTypeAdapter(Long.class, new LongDefault0Adapter())
               .registerTypeAdapter(long.class, new LongDefault0Adapter())

               .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
       return  gson;
   };

    /**
     * 把一个map变成json字符串
     *
     * @param map
     * @return
     */
    public static String parseMapToJson(Map<?, ?> map) {
        try {
            Gson gson =getGson();
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把一lIst变成json字符串
     */
    public static String parseListToJson(List list) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            return gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String parseObjectToJson(Object object) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            return gson.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        T t = null;
        try {
            t = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    /**
     * 把json字符串变成map
     *
     * @param json
     * @return
     */
    public static TreeMap<String, Object> parseJsonToMap(String json) {
        Gson gson =  new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
               /* .registerTypeAdapter(
                        new TypeToken<TreeMap<String, Object>>(){}.getType(),
                        new JsonDeserializer<TreeMap<String, Object>>() {
                            @Override
                            public TreeMap<String, Object> deserialize(
                                    JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {

                                TreeMap<String, Object> treeMap = new TreeMap<>();
                                JsonObject jsonObject = json.getAsJsonObject();
                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                for (Map.Entry<String, JsonElement> entry : entrySet) {
                                    treeMap.put(entry.getKey(), entry.getValue());
                                }
                                return treeMap;
                            }
                        })*/
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Type type = new TypeToken<TreeMap<String, Object>>() {
        }.getType();
        TreeMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }
/*	public static <T> List<T> changeGsonToList(String gsonString, T cls) {
        Gson gson = new Gson();
		List<T> list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
		}.getType());
		return list;
	}*/


    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, Type type) {
        //Gson gson = new Gson();
        //要解释时间的  必须写成这样子的
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
               // .registerTypeAdapter(Date.class, new DateDefault0Adapter())
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<T> list =null;
        try {
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }



    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) throws Exception {
        List<T> lst =  new ArrayList<T>();

        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            lst.add(new Gson().fromJson(elem, clazz));
        }

        return lst;
    }
    // 将Json数组解析成相应的映射对象列表
    public static <T> ArrayList<T> parseJsonArray(String jsonStr, Class myClass)throws Exception {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
               // .registerTypeAdapter(Date.class, new DateDefault0Adapter())
                .registerTypeAdapter(Date.class, new Default2DateTypeAdapter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")))
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

       Type type = new ListParameterizedType(myClass);

        return  gson.fromJson(jsonStr,type);
    }

    private static class ListParameterizedType implements ParameterizedType {
        private Type type;
        private ListParameterizedType(Type type) {
            this.type = type;
        }
        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {type};
        }
        @Override
        public Type getRawType() {
            return ArrayList.class;
        }
        @Override
        public Type getOwnerType() {
            return null;
        }
        // implement equals method too! (as per javadoc)
    }






/*	public static <T> List<T> parseJsonToList2(String json) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
		return list;
	}*/

    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json))
            return null;
        if (!json.contains(key))
            return "";
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }

        return value;
    }
    public static class IntegerDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                    return 0;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }


    public static class DateDefault0Adapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                    return null;
                }
            } catch (Exception ignore) {
            }
            try {
                return TimeUtils.string2Date(json.getAsString());
            } catch (Exception e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(String.valueOf(src));
        }
    }





    public static class DoubleDefault0Adapter implements JsonSerializer<Double>, JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为double类型,如果后台返回""或者null,则返回0.00
                    return 0.00;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsDouble();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
    public static class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
        @Override
        public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                    return 0l;
                }
            } catch (Exception ignore) {
            }
            try {
                return json.getAsLong();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
    final static class Default2DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {



        private final DateFormat enUsFormat;
        private final DateFormat localFormat ;

        Default2DateTypeAdapter() {
            this(DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US),
                    DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT));
        }

        Default2DateTypeAdapter(String datePattern) {
            this(new SimpleDateFormat(datePattern, Locale.US), new SimpleDateFormat(datePattern));
        }

        Default2DateTypeAdapter(int style) {

            this(DateFormat.getDateInstance(style, Locale.US), DateFormat.getDateInstance(style));
        }

        public Default2DateTypeAdapter(int dateStyle, int timeStyle) {
            this(DateFormat.getDateTimeInstance(dateStyle, timeStyle, Locale.US),
                    DateFormat.getDateTimeInstance(dateStyle, timeStyle));
        }

        Default2DateTypeAdapter(DateFormat enUsFormat, DateFormat localFormat) {
            this.enUsFormat = enUsFormat;
            this.localFormat = localFormat;
        }

        // These methods need to be synchronized since JDK DateFormat classes are not thread-safe
        // See issue 162
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            synchronized (localFormat) {
                String dateFormatAsString = enUsFormat.format(src);
                return new JsonPrimitive(dateFormatAsString);
            }
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {


            if (json.getAsString().equals("") || json.getAsString().equals("null")) {
                return null;
            }

            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            Date date = deserializeToDate(json);
            if (typeOfT == Date.class) {
                return date;
            } else if (typeOfT == Timestamp.class) {
                return new Timestamp(date.getTime());
            } else if (typeOfT == java.sql.Date.class) {
                return new java.sql.Date(date.getTime());
            } else {
                throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
            }
        }

        private Date deserializeToDate(JsonElement json) {
            synchronized (localFormat) {
                try {
                    return localFormat.parse(json.getAsString());
                } catch (ParseException ignored) {
                }
                try {
                    return enUsFormat.parse(json.getAsString());
                } catch (ParseException ignored) {
                }
                try {
                    return ISO8601Utils.parse(json.getAsString(), new ParsePosition(0));
                } catch (ParseException e) {
                    throw new JsonSyntaxException(json.getAsString(), e);
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Default2DateTypeAdapter.class.getSimpleName());
            sb.append('(').append(localFormat.getClass().getSimpleName()).append(')');
            return sb.toString();
        }


    }
    // 使用Gson进行解析List对象
    public static <T> ArrayList<T> getObjects(String s, Class<T[]> clazz) {
        ArrayList<T> ts = new ArrayList<>();
        try {
            T[] arr = new Gson().fromJson(s, clazz);
            ts.addAll(Arrays.asList(arr));
        } catch (Exception ignore) {
        }
        return ts;
    }


}
