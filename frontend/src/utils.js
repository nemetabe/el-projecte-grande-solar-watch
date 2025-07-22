import { useNavigate } from "react-router-dom";

export async function fetchData(path, method = "GET", body = null, jwt = null, hasRequestBody = true) {
    const options = {
        method,
        headers: {
            "Content-Type": "application/json",
        },
    };

    if (jwt) {
        options.headers.authorization = `Bearer ${jwt}`;
    }
    
    if (method !== "GET" && body) {
        options.body = JSON.stringify(body);
    }

    const response =  await fetch(`/api/${path}`, options);

    // if (response.status === 401) {
    //     localStorage.setItem("solAndRJwt", "null");
    //     window.location.href = "/";
    // }

    if (hasRequestBody) {
        return response.json();
    }
}
