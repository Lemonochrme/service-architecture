/**
 * Fetches data from the given URL and returns the parsed JSON response.
 * 
 * @param {string} url - The URL to fetch data from.
 * @returns {Promise<Array>} - A promise that resolves to an array from the JSON response.
 */
function fetchData(url) {
    return fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',  // Expecting JSON data from the server
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();  // Parse the JSON response
    })
    .then(data => {
        return data;  // Return the array from the JSON response
    })
    .catch(error => {
        console.error('There was a problem with the fetch operation:', error);
        return [];  // Return an empty array in case of an error
    });
}

function createOption(id, name) {
    const option = document.createElement('option');
    option.value = id;
    option.textContent = name;
    return option;
}
