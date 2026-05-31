import {useEffect, useState} from "react";
import axios from "axios";

function App() {

    const [products, setProducts] = useState([]);


    const [newProduct, setNewProduct] = useState({
        name: "", category: "", price: "", quantity: ""
    });
    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("");
    const [editingProductId, setEditingProductId] = useState(null);
    const [searchKeyword, setSearchKeyword] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [sortBy, setSortBy] = useState("id");
    const [direction, setDirection] = useState("asc");
    const [selectedCategory, setSelectedCategory] = useState("");
   // const [viewMode, setViewMode] = useState("normal");
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetchProducts();
        fetchCategories();
    }, [currentPage, sortBy, direction]);

    const fetchProducts = () => {
        let url = "";

        if (searchKeyword.trim() !== "" && selectedCategory !== "") {
            url = `http://localhost:8080/products/filter?keyword=${searchKeyword}&category=${selectedCategory}&page=${currentPage}&size=5&sortBy=${sortBy}&direction=${direction}`;
        } else if (searchKeyword.trim() !== "") {
            url = `http://localhost:8080/products/search/page?keyword=${searchKeyword}&page=${currentPage}&size=5&sortBy=${sortBy}&direction=${direction}`;
        } else if (selectedCategory !== "") {
            url = `http://localhost:8080/products/category/${selectedCategory}/page?page=${currentPage}&size=5&sortBy=${sortBy}&direction=${direction}`;
        } else {
            url = `http://localhost:8080/products/page?page=${currentPage}&size=5&sortBy=${sortBy}&direction=${direction}`;
        }

        axios
            .get(url)
            .then((response) => {
                setProducts(response.data.content);
                setTotalPages(response.data.totalPages);
            })
            .catch((error) => {
                console.error("Error fetching products:", error);
                setProducts([]);
                setTotalPages(0);
            });
    };
    const fetchCategories = () => {
        axios
            .get("http://localhost:8080/products")
            .then((response) => {
                const uniqueCategories = [
                    ...new Set(response.data.map((product) => product.category))
                ];

                setCategories(uniqueCategories);
            })
            .catch((error) => {
                console.error("Error fetching categories:", error);
            });
    };
    const applyFilters = () => {
        setCurrentPage(0);
        fetchProducts();
    };
    const handleInputChange = (event) => {
        const {name, value} = event.target;

        setNewProduct({
            ...newProduct, [name]: value
        });
    };
    const addProduct = () => {

        if (
            newProduct.name.trim() === "" ||
            newProduct.category.trim() === "" ||
            newProduct.price === "" ||
            newProduct.quantity === ""
        ) {
            setMessage("All fields are required");
            setMessageType("danger");

            setTimeout(() => {
                setMessage("");
            }, 2000);

            return;
        }

        if (!Number.isInteger(Number(newProduct.quantity))) {
            setMessage("Quantity must be a whole number");
            setMessageType("danger");

            setTimeout(() => {
                setMessage("");
            }, 2000);

            return;
        }

        axios
            .post("http://localhost:8080/products", newProduct)
            .then(() => {
                fetchProducts();
                fetchCategories();
                setMessage("Product added successfully");
                setMessageType("success");
                setTimeout(() => {
                    setMessage("");
                }, 1000);
                setNewProduct({
                    name: "", category: "", price: "", quantity: ""
                });
            })
            .catch((error) => {
                console.error("Error adding product:", error);
            });
    };
    const deleteProduct = (id) => {
        axios
            .delete(`http://localhost:8080/products/${id}`)
            .then(() => {
                fetchProducts();
                fetchCategories();
                setMessage("Product deleted successfully");
                setMessageType("danger");
                setTimeout(() => {
                    setMessage("");
                }, 1000);
            })
            .catch((error) => {
                console.error("Error deleting product:", error);
            });
    };
    const editProduct = (product) => {
        setEditingProductId(product.id);

        setNewProduct({
            name: product.name, category: product.category, price: product.price, quantity: product.quantity
        });
    };
    const updateProduct = () => {
        axios
            .put(`http://localhost:8080/products/${editingProductId}`, newProduct)
            .then(() => {

                fetchProducts();
                fetchCategories();

                setMessage("Product updated successfully");
                setMessageType("warning");

                setTimeout(() => {
                    setMessage("");
                }, 1000);

                setEditingProductId(null);

                setNewProduct({
                    name: "", category: "", price: "", quantity: ""
                });
            })
            .catch((error) => {
                console.error("Error updating product:", error);
            });
    };
    const cancelEdit = () => {

        setEditingProductId(null);

        setNewProduct({
            name: "", category: "", price: "", quantity: ""
        });
    };
    const searchProducts = () => {
        setCurrentPage(0);
        setViewMode("search");
    };
    const resetSearch = () => {
        setSearchKeyword("");
        setSelectedCategory("");
        setViewMode("normal");
        setCurrentPage(0);
    };
    const filterByCategory = () => {
        if (selectedCategory === "") {
            setViewMode("normal");
            setCurrentPage(0);
            return;
        }

        setCurrentPage(0);

        if (viewMode === "category") {
            fetchProducts();
        } else {
            setViewMode("category");
        }
    };
    const handleSort = (field) => {
        if (sortBy === field) {
            setDirection(direction === "asc" ? "desc" : "asc");
        } else {
            setSortBy(field);
            setDirection("asc");
        }

        setCurrentPage(0);
    };
    const getSortIcon = (field) => {

        if (sortBy === field) {
            return direction === "asc" ? "↑" : "↓";
        }

        return "⇅";
    };

    return (<div>

        <nav className="navbar navbar-dark bg-dark">
            <div className="container-fluid">
          <span className="navbar-brand mb-0 h1">
            Inventory Management System
          </span>
            </div>
        </nav>

        <div className="container mt-4">

            <h2 className="text-center">Product Dashboard</h2>
            {message && (<div className={`alert alert-${messageType} mt-3`}>
                    {message}
                </div>)}

            <div className="card p-4 mt-4">

                <h4>
                    {editingProductId ? "Update Product" : "Add Product"}
                </h4>

                <div className="row mt-3">

                    <div className="col-md-3">
                        <input
                            type="text"
                            name="name"
                            className="form-control"
                            placeholder="Product Name"
                            value={newProduct.name}
                            onChange={handleInputChange}
                        />
                    </div>

                    <div className="col-md-3">
                        <input
                            type="text"
                            name="category"
                            className="form-control"
                            placeholder="Category"
                            value={newProduct.category}
                            onChange={handleInputChange}
                        />
                    </div>

                    <div className="col-md-2">
                        <input
                            type="number"
                            name="price"
                            className="form-control"
                            placeholder="Price"
                            value={newProduct.price}
                            onChange={handleInputChange}
                        />
                    </div>

                    <div className="col-md-2">
                        <input
                            type="number"
                            name="quantity"
                            className="form-control"
                            placeholder="Quantity"
                            value={newProduct.quantity}
                            onChange={handleInputChange}
                            min="0"
                            step="1"
                        />
                    </div>

                    <div className="col-md-2">
                        <button
                            className="btn btn-primary w-100"
                            onClick={editingProductId ? updateProduct : addProduct}
                        >
                            {editingProductId ? "Update Product" : "Add Product"}
                        </button>
                        {editingProductId && (<button
                                className="btn btn-secondary w-100 mt-2"
                                onClick={cancelEdit}
                            >
                                Cancel
                            </button>)}
                    </div>

                </div>
            </div>

            <div className="card p-4 mt-4">
                <h4>Search and Filter Products</h4>

                <div className="row mt-3">
                    <div className="col-md-5">

                        <input
                            type="text"
                            className="form-control"
                            placeholder="Search by product name"
                            value={searchKeyword}
                            onChange={(event) => setSearchKeyword(event.target.value)}
                        />
                    </div>

                    <div className="col-md-2">
                        <button className="btn btn-success w-100" onClick={applyFilters}>
                            Search
                        </button>
                    </div>

                    <div className="col-md-3">
                        <select
                            className="form-select"
                            value={selectedCategory}
                            onChange={(event) => setSelectedCategory(event.target.value)}
                        >
                            <option value="">All Categories</option>
                            {categories.map((category) => (<option
                                    key={category}
                                    value={category}
                                >
                                    {category}
                                </option>))}
                        </select>
                    </div>

                    <div className="col-md-2">
                        <button className="btn btn-info w-100" onClick={applyFilters}>
                            Filter
                        </button>
                    </div>
                </div>

                <div className="row mt-3">
                    <div className="col-md-2">
                        <button
                            className="btn btn-secondary w-100"
                            onClick={() => {
                                setSearchKeyword("");
                                setSelectedCategory("");
                                setCurrentPage(0);
                                fetchProducts();
                            }}
                        >
                            Reset All
                        </button>
                    </div>
                </div>
            </div>


            <div className="card p-4 mt-4">
                <h4>Products</h4>

                <table className="table table-bordered table-striped mt-3">

                    <thead className="table-dark">
                    <tr>
                        <th>
                            <div className="d-flex justify-content-between align-items-center">
                                <span>ID</span>
                                <span onClick={() => handleSort("id")} style={{cursor: "pointer"}}
                                >
                                        {getSortIcon("id")}
                                    </span>
                            </div>
                        </th>
                        <th>
                            <div className="d-flex justify-content-between align-items-center">
                                <span>Name</span>
                                <span onClick={() => handleSort("name")} style={{cursor: "pointer"}}
                                >
                                            {getSortIcon("name")}
                                        </span>
                            </div>
                        </th>
                        <th>
                            <div className="d-flex justify-content-between align-items-center">
                                <span>Category</span>
                                <span onClick={() => handleSort("category")} style={{cursor: "pointer"}}
                                >
                                            {getSortIcon("category")}
                                        </span>
                            </div>
                        </th>
                        <th>
                            <div className="d-flex justify-content-between align-items-center">
                                <span>Price</span>
                                <span onClick={() => handleSort("price")} style={{cursor: "pointer"}}
                                >
                                            {getSortIcon("price")}
                                        </span>
                            </div>
                        </th>
                        <th>
                            <div className="d-flex justify-content-between align-items-center">
                                <span>Quantity</span>
                                <span onClick={() => handleSort("quantity")} style={{cursor: "pointer"}}
                                >
                                            {getSortIcon("quantity")}
                                        </span>
                            </div>
                        </th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody>
                    {products.map((product) => (<tr key={product.id}>
                            <td>{product.id}</td>
                            <td>{product.name}</td>
                            <td>{product.category}</td>
                            <td>{product.price}</td>
                            <td>{product.quantity}</td>
                            <td>
                                <button
                                    className="btn btn-warning btn-sm me-2"
                                    onClick={() => editProduct(product)}
                                >
                                    Edit
                                </button>

                                <button
                                    className="btn btn-danger btn-sm"
                                    onClick={() => deleteProduct(product.id)}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>))}
                    </tbody>

                </table>
                <div className="d-flex justify-content-center align-items-center mt-3">
                    <button
                        className="btn btn-secondary me-3"
                        disabled={currentPage === 0}
                        onClick={() => setCurrentPage(currentPage - 1)}
                    >
                        Previous
                    </button>

                    <span>
        Page {currentPage + 1} of {totalPages}
    </span>

                    <button
                        className="btn btn-secondary ms-3"
                        disabled={currentPage + 1 >= totalPages}
                        onClick={() => setCurrentPage(currentPage + 1)}
                    >
                        Next
                    </button>
                </div>
            </div>
            </div>
        </div>

        );
        }

        export default App;