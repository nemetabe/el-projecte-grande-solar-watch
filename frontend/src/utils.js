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

    try {
        const response = await fetch(`/api/${path}`, options);
        
        if (response.status === 401) {
            localStorage.setItem("solAndRJwt", null);
            window.location.href = "/browse";
            throw new Error("Unauthorized");
        }

        if (response.status === 204 || response.status === 201) {
            return null;
        }

        const contentType = response.headers.get("content-type");
        if (contentType && contentType.includes("application/json")) {
            return await response.json();
        }

        return response;
    } catch (error) {
        console.error('Error in fetchData:', {
            path,
            method,
            error: error.message,
            hasRequestBody
        });
        throw error;
    }
}
