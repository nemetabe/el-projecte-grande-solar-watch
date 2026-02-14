import React from "react";
import FormInput from "../common/FormInput";

function Login({ handleSubmit, handleChange, formData }) {
  return (
    <div className="form-container sign-in-container">
      <form onSubmit={handleSubmit}>
        <h5>Welcome back!</h5>
        <span>or use your account</span>

        <FormInput
          id="username"
          name="username"
          placeholder="Username"
          value={formData.username}
          onChange={handleChange}
        />

        <FormInput
          id="password"
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
        />

        <a href="#">Forgot your password?</a>
        <button>Sign In</button>
      </form>
    </div>
  );
}

export default Login;