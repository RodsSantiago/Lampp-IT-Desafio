import React from "react";

//yarn add react-router-dom - mecanismo ais adotado pela comunidade para fazer navegacao //yarn add @types/react-router-dom para ficar em type script
import { BrowserRouter, Switch ,Route } from 'react-router-dom';
import Navbar from "./core/components/NavBar";
import Admin from "./core/pages/Admin";
import Catalog from "./core/pages/Catalog";
import ProductDetails from "./core/pages/Catalog/components/ProductDetails";
import Home from "./core/pages/Home";
import CatalogCompany from "core/pages/CatalogCompany";

const Routess = () => (
    <BrowserRouter>
    <Navbar />
    <Switch>

        <Route path = "/" exact>
            <Home />

        </Route>

        <Route path = "/products" exact>
            <Catalog />
        </Route>

        <Route path = "/company" exact>
            <CatalogCompany />
        </Route>

        <Route path = "/products/:productId">
            <ProductDetails />
        </Route>

        <Route path = "/admin">
            <Admin />

        </Route>

    </Switch>
    </BrowserRouter>

);

export default Routess;