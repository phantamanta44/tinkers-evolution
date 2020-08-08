package xyz.phanta.tconevo.client.util;

import net.minecraft.client.renderer.block.model.IBakedModel;

public interface DelegatingModel<K, M extends DelegatingModel<K, M>> extends IBakedModel {

    IBakedModel getParentModel();

    M wrapDelegate(K delegateKey);

}
