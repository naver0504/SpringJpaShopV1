package jpabook.jpashop.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1091322645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final ListPath<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory> categories = this.<jpabook.jpashop.domain.Category, jpabook.jpashop.domain.QCategory>createList("categories", jpabook.jpashop.domain.Category.class, jpabook.jpashop.domain.QCategory.class, PathInits.DIRECT2);

    public final ListPath<jpabook.jpashop.domain.Comment, jpabook.jpashop.domain.QComment> comments = this.<jpabook.jpashop.domain.Comment, jpabook.jpashop.domain.QComment>createList("comments", jpabook.jpashop.domain.Comment.class, jpabook.jpashop.domain.QComment.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final jpabook.jpashop.domain.QItemImg itemImg;

    public final jpabook.jpashop.domain.QMember member;

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.itemImg = inits.isInitialized("itemImg") ? new jpabook.jpashop.domain.QItemImg(forProperty("itemImg"), inits.get("itemImg")) : null;
        this.member = inits.isInitialized("member") ? new jpabook.jpashop.domain.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

