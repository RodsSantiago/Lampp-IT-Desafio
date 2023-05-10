import { type } from "os";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import './styles.scss';
import { ReactComponent as ArrowIcon } from '../../../../assets/images/arrow.svg'
import { ReactComponent as ProductImage } from '../../../../assets/images/product.svg'
import { Link } from "react-router-dom";
import { Product } from "../../../../types/Product";
import ProductPrice from "../../../../components/ProductPrice";
import { makeRequest } from "../../../../utils/request";
import ProductInfoLoader from "../Loaders/ProductInfoLoader";
import ProductDescriptionLoader from "../Loaders/ProductDescriptionLoader";

type ParamsType = {
    productId: string;
}


const ProductDetails = () => {
    const { productId } = useParams<ParamsType>();
    const [product, setProduct] = useState<Product>();

    //criar um estado pro loader
    const [isLoading, setIsLoading] = useState(false);

    //Quando o componente iniciar buscar as infos do produto - usar UseEffect para quando iniciar 
    //como dependecia do use effect ficaria dentro do arrey - como nao tem fica o array vazio
    useEffect(() => {
        setIsLoading(true);
        //utilizar a funcao criada makerequest para chamar o request - e passar uma URL do recurso que quer ser acessado
        //colcar como o que passamos como variavel no make request como dependencia do useEffect
        makeRequest({ url: `/products/${productId}` })
            .then(response => setProduct(response.data))
            .finally(() => setIsLoading(false));
    }, [productId]);

    return (
        <div className="product-details-container">
            <div className="card-base border-radius-20 product-details">
                <Link to="/products" className="produc-details-goback">
                    <ArrowIcon className="icon-goback" />
                    <h1 className="text-goback">VOLTAR</h1>
                </Link>
                <div className="row">
                    <div className="col-6 pr-5">
                        {isLoading ? <ProductInfoLoader /> : (<>
                            <div className="product-details-card text-center">
                                <img src={product?.imgUrl} alt={product?.name} className="product-details-image" />
                            </div>
                            <h1 className="product-details-name">
                                {product?.name}
                            </h1>
                            {product?.price && <ProductPrice price={product?.price} />}
                        </>)}
                    </div>

                    <div className="col-6 product-details-card">
                        {isLoading ? <ProductDescriptionLoader /> : (
                            <>
                                <h1 className="product-description-title">Descricao do produto</h1>
                                <p className="product-description-text">
                                    {product?.description}
                                </p>
                            </>
                        )}
                    </div>
                </div>
            </div>

        </div>
    );
};

export default ProductDetails;