package xyz.phanta.tconevo.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.phantamanta44.libnine.util.helper.JsonUtils9;
import net.minecraft.nbt.*;
import net.minecraftforge.common.util.Constants;

import java.util.Map;

public interface ImmutableNbt<T extends NBTBase> {

    @SuppressWarnings("unchecked")
    static <T extends NBTBase> ImmutableNbt<T> wrap(T tag) {
        switch (tag.getId()) {
            case Constants.NBT.TAG_COMPOUND: {
                ImmutableMap.Builder<String, ImmutableNbt<?>> entries = ImmutableMap.builder();
                NBTTagCompound compoundTag = (NBTTagCompound)tag;
                for (String key : compoundTag.getKeySet()) {
                    entries.put(key, wrap(compoundTag.getTag(key)));
                }
                return (ImmutableNbt<T>)new ImmCompound(entries.build());
            }
            case Constants.NBT.TAG_LIST: {
                ImmutableList.Builder<ImmutableNbt<?>> entries = ImmutableList.builder();
                for (NBTBase entry : (NBTTagList)tag) {
                    entries.add(wrap(entry));
                }
                return (ImmutableNbt<T>)new ImmList(entries.build());
            }
            case Constants.NBT.TAG_STRING:
                return (ImmutableNbt<T>)new ImmString(((NBTTagString)tag).getString());
            case Constants.NBT.TAG_INT:
                return (ImmutableNbt<T>)new ImmInteger(((NBTTagInt)tag).getInt());
            case Constants.NBT.TAG_BYTE:
                return (ImmutableNbt<T>)new ImmByte(((NBTTagByte)tag).getByte());
            default:
                throw new UnsupportedOperationException("NBT tag type not supported: " + tag.getClass().getSimpleName());
        }
    }

    static ImmutableNbt<?> parse(JsonElement dto) {
        if (dto.isJsonObject()) {
            return parseObject(dto.getAsJsonObject());
        } else if (dto.isJsonArray()) {
            ImmutableList.Builder<ImmutableNbt<?>> entries = ImmutableList.builder();
            for (JsonElement entry : dto.getAsJsonArray()) {
                entries.add(parse(entry));
            }
            return new ImmList(entries.build());
        } else if (dto.isJsonPrimitive()) {
            JsonPrimitive primDto = dto.getAsJsonPrimitive();
            if (primDto.isNumber()) {
                return new ImmInteger(primDto.getAsInt()); // assume it's an integer; whatever lol
            } else if (primDto.isString()) {
                return new ImmString(primDto.getAsString());
            } else if (primDto.isBoolean()) {
                return new ImmByte(primDto.getAsBoolean() ? (byte)1 : 0); // nbt encodes booleans as bytes
            }
        }
        throw new IllegalArgumentException("Cannot parse NBT from JSON: " + JsonUtils9.GSON.toJson(dto));
    }

    static ImmCompound parseObject(JsonObject dto) {
        ImmutableMap.Builder<String, ImmutableNbt<?>> entries = ImmutableMap.builder();
        for (Map.Entry<String, JsonElement> entry : dto.entrySet()) {
            entries.put(entry.getKey(), parse(entry.getValue()));
        }
        return new ImmCompound(entries.build());
    }

    int getTagType();

    T createTag();

    default T write(T tag) {
        return createTag();
    }

    @SuppressWarnings("unchecked")
    default T writeUnchecked(NBTBase tag) {
        return write((T)tag);
    }

    class ImmCompound implements ImmutableNbt<NBTTagCompound> {

        private final Map<String, ImmutableNbt<?>> entries;

        public ImmCompound(ImmutableMap<String, ImmutableNbt<?>> entries) {
            this.entries = entries;
        }

        @Override
        public int getTagType() {
            return Constants.NBT.TAG_COMPOUND;
        }

        @Override
        public NBTTagCompound createTag() {
            return write(new NBTTagCompound());
        }

        @Override
        public NBTTagCompound write(NBTTagCompound tag) {
            entries.forEach((k, v) -> {
                if (tag.hasKey(k, v.getTagType())) {
                    NBTBase value = tag.getTag(k);
                    NBTBase newValue = v.writeUnchecked(value);
                    if (value != newValue) {
                        tag.setTag(k, newValue);
                    }
                } else {
                    tag.setTag(k, v.createTag());
                }
            });
            return tag;
        }

    }

    class ImmList implements ImmutableNbt<NBTTagList> {

        private final ImmutableList<ImmutableNbt<?>> entries;

        public ImmList(ImmutableList<ImmutableNbt<?>> entries) {
            this.entries = entries;
        }

        @Override
        public int getTagType() {
            return Constants.NBT.TAG_LIST;
        }

        @Override
        public NBTTagList createTag() {
            return write(new NBTTagList());
        }

        @Override
        public NBTTagList write(NBTTagList tag) {
            for (ImmutableNbt<?> entry : entries) {
                tag.appendTag(entry.createTag()); // don't merge lists; just append
            }
            return tag;
        }

    }

    class ImmString implements ImmutableNbt<NBTTagString> {

        private final String value;

        public ImmString(String value) {
            this.value = value;
        }

        @Override
        public int getTagType() {
            return Constants.NBT.TAG_STRING;
        }

        @Override
        public NBTTagString createTag() {
            return new NBTTagString(value);
        }

    }

    class ImmInteger implements ImmutableNbt<NBTTagInt> {

        private final int value;

        public ImmInteger(int value) {
            this.value = value;
        }

        @Override
        public int getTagType() {
            return Constants.NBT.TAG_INT;
        }

        @Override
        public NBTTagInt createTag() {
            return new NBTTagInt(value);
        }

    }

    class ImmByte implements ImmutableNbt<NBTTagByte> {

        private final byte value;

        public ImmByte(byte value) {
            this.value = value;
        }

        @Override
        public int getTagType() {
            return Constants.NBT.TAG_BYTE;
        }

        @Override
        public NBTTagByte createTag() {
            return new NBTTagByte(value);
        }

    }

}
