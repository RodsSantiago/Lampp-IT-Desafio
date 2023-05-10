import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProductCard from "./components/ProductCard";
import './styles.scss';
import axios from "axios";
import { makeRequest } from "../../utils/request";
import { ProductResponse } from "../../types/Product";
import ProductCardLoader from "./components/Loaders/ProductCardLoader";
import Pagination from "core/components/Pagination";

const Catalog = () => {
    //quando o componente iniciar, buscar a lista de produtos
    //quando a lista de produtos estiver disponivel popular o um estado no componente, e listar os produtos dinamicamente.
    const [productsResponse, setProductsResponse] = useState<ProductResponse>();

    //criar um estado pro loader
    const [isLoading, setIsLoading] = useState(false);

    //criar um estado para deixar as cores dinamicas
    const [activePage, setActivePage] = useState(0);

    //quando o componente iniciar, bucar a lista de produtos
    useEffect(() => {
        const params = {
            page: activePage,
            linesPerPage: 12
        }

        //antes de fazer a requisicao precisa iniciar o loader
        setIsLoading(true);
        makeRequest({ url: '/products', params })
            .then(response => setProductsResponse(response.data))
            .finally(() =>{
                //finalizar o loader
                setIsLoading(false);
            })
    }, [activePage]);

    return (
        <div className="catalog-container">
            <h1 className="catalog-title">
                Catálogo de produtos
            </h1>   
            <div className="catalog-products">
                {isLoading ? <ProductCardLoader /> : (productsResponse?.content.map(product => (
                    <Link to={`/products/${product.id}`} key={product.id}>
                        <ProductCard product={product} />
                    </Link>
                )))}
            </div>
            {productsResponse && (
                <Pagination 
                    totalPages={productsResponse.totalPages}
                    activePage={activePage}
                    onChange={page => setActivePage(page)}
                    />
                 )}
        </div>

    );
}

export default Catalog;