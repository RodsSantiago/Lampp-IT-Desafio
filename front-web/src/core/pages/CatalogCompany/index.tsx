import React, { useEffect, useState } from "react";
import './styles.scss';
import CompanyCard from "./components/CompanyCard";
import { ProductResponse } from "core/types/Product";
import Pagination from "core/components/Pagination";
import { makeRequest } from "core/utils/request";
import { Link } from "react-router-dom";
import Catalog from "../Catalog";
import ProductCardLoader from "../Catalog/components/Loaders/ProductCardLoader";
import ProductCard from "../Catalog/components/ProductCard";
import { CompanyResponse } from "core/types/Company";

const CatalogCompany = () => {
    //quando o componente iniciar, buscar a lista de produtos
    //quando a lista de produtos estiver disponivel popular o um estado no componente, e listar os produtos dinamicamente.
    const [companyResponse, setCompanyResponse] = useState<CompanyResponse>();

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
        makeRequest({ url: '/company', params })
            .then(response => setCompanyResponse(response.data))
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
                {isLoading ? <ProductCardLoader /> : (companyResponse?.content.map(company => (
                    <Link to={`/company/${company.id}`} key={company.id}>
                        <CompanyCard company={company} />
                    </Link>
                )))}
            </div>
            {companyResponse && (
                <Pagination 
                    totalPages={companyResponse.totalPages}
                    activePage={activePage}
                    onChange={page => setActivePage(page)}
                    />
                 )}
        </div>

    );
}

export default CatalogCompany;