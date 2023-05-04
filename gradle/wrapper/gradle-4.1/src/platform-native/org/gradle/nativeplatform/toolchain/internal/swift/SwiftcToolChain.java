/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.nativeplatform.toolchain.internal.swift;

import com.google.common.collect.Maps;
import org.gradle.internal.file.PathToFileResolver;
import org.gradle.internal.operations.BuildOperationExecutor;
import org.gradle.internal.os.OperatingSystem;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.nativeplatform.internal.CompilerOutputFileNamingSchemeFactory;
import org.gradle.nativeplatform.platform.NativePlatform;
import org.gradle.nativeplatform.platform.internal.NativePlatformInternal;
import org.gradle.nativeplatform.toolchain.Swiftc;
import org.gradle.nativeplatform.toolchain.SwiftcPlatformToolChain;
import org.gradle.nativeplatform.toolchain.internal.ExtendableToolChain;
import org.gradle.nativeplatform.toolchain.internal.NativeToolChainInternal;
import org.gradle.nativeplatform.toolchain.internal.PlatformToolProvider;
import org.gradle.nativeplatform.toolchain.internal.ToolType;
import org.gradle.nativeplatform.toolchain.internal.UnavailablePlatformToolProvider;
import org.gradle.nativeplatform.toolchain.internal.gcc.version.CompilerMetaDataProviderFactory;
import org.gradle.nativeplatform.toolchain.internal.tools.CommandLineToolSearchResult;
import org.gradle.nativeplatform.toolchain.internal.tools.DefaultCommandLineToolConfiguration;
import org.gradle.nativeplatform.toolchain.internal.tools.ToolSearchPath;
import org.gradle.platform.base.internal.toolchain.ToolChainAvailability;
import org.gradle.process.internal.ExecActionFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class SwiftcToolChain extends ExtendableToolChain<SwiftcPlatformToolChain> implements Swiftc, NativeToolChainInternal {
    public static final String DEFAULT_NAME = "swiftc";

    private final CompilerOutputFileNamingSchemeFactory compilerOutputFileNamingSchemeFactory;
    private final Instantiator instantiator;
    private final ToolSearchPath toolSearchPath;
    private final ExecActionFactory execActionFactory;
    private final Map<NativePlatform, PlatformToolProvider> toolProviders = Maps.newHashMap();

    public SwiftcToolChain(String name, BuildOperationExecutor buildOperationExecutor, OperatingSystem operatingSystem, PathToFileResolver fileResolver, ExecActionFactory execActionFactory,
                           CompilerOutputFileNamingSchemeFactory compilerOutputFileNamingSchemeFactory, CompilerMetaDataProviderFactory compilerMetaDataProviderFactory, Instantiator instantiator) {
        this(name, buildOperationExecutor, operatingSystem, fileResolver, execActionFactory, compilerOutputFileNamingSchemeFactory, new ToolSearchPath(operatingSystem), compilerMetaDataProviderFactory, instantiator);
    }

    SwiftcToolChain(String name, BuildOperationExecutor buildOperationExecutor, OperatingSystem operatingSystem, PathToFileResolver fileResolver, ExecActionFactory execActionFactory,
                    CompilerOutputFileNamingSchemeFactory compilerOutputFileNamingSchemeFactory, ToolSearchPath tools, CompilerMetaDataProviderFactory compilerMetaDataProviderFactory, Instantiator instantiator) {
        super(name, buildOperationExecutor, operatingSystem, fileResolver);
        this.compilerOutputFileNamingSchemeFactory = compilerOutputFileNamingSchemeFactory;
        this.instantiator = instantiator;
        this.toolSearchPath = tools;
        this.execActionFactory = execActionFactory;
    }

    @Override
    public List<File> getPath() {
        return toolSearchPath.getPath();
    }

    @Override
    public void path(Object... pathEntries) {
        for (Object path : pathEntries) {
            toolSearchPath.path(resolve(path));
        }
    }

    private PlatformToolProvider createPlatformToolProvider(NativePlatformInternal targetPlatform) {
        DefaultSwiftcPlatformToolChain configurableToolChain = instantiator.newInstance(DefaultSwiftcPlatformToolChain.class, targetPlatform);
        addDefaultTools(configurableToolChain);
        configureActions.execute(configurableToolChain);

        CommandLineToolSearchResult compiler = toolSearchPath.locate(ToolType.SWIFT_COMPILER, "swiftc");
        ToolChainAvailability result = new ToolChainAvailability();
        result.mustBeAvailable(compiler);
        if (!result.isAvailable()) {
            return new UnavailablePlatformToolProvider(targetPlatform.getOperatingSystem(), result);
        }

        return new SwiftPlatformToolProvider(buildOperationExecutor, targetPlatform.getOperatingSystem(), toolSearchPath, configurableToolChain, execActionFactory, compilerOutputFileNamingSchemeFactory);
    }

    @Override
    public PlatformToolProvider select(NativePlatformInternal targetPlatform) {
        PlatformToolProvider toolProvider = toolProviders.get(targetPlatform);
        if (toolProvider == null) {
            toolProvider = createPlatformToolProvider(targetPlatform);
            toolProviders.put(targetPlatform, toolProvider);
        }
        return toolProvider;
    }

    private void addDefaultTools(DefaultSwiftcPlatformToolChain toolChain) {
        toolChain.add(instantiator.newInstance(DefaultCommandLineToolConfiguration.class, ToolType.SWIFT_COMPILER));
        toolChain.add(instantiator.newInstance(DefaultCommandLineToolConfiguration.class, ToolType.LINKER));
    }

    @Override
    protected String getTypeName() {
        return "Swift Compiler";
    }
}
